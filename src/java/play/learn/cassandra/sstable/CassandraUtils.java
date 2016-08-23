package play.learn.cassandra.sstable;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import jline.ConsoleReader;

import org.apache.cassandra.config.CFMetaData;
import org.apache.cassandra.config.Config;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.config.Schema;
import org.apache.cassandra.db.marshal.AbstractType;
import org.apache.cassandra.dht.Murmur3Partitioner;
import org.apache.cassandra.io.sstable.Component;
import org.apache.cassandra.io.sstable.Descriptor;
import org.apache.cassandra.io.sstable.SSTableReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.UserType;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.MinMaxPriorityQueue;

public class CassandraUtils {
	private static final Logger logger = LoggerFactory.getLogger(CassandraUtils.class);
	private static final AtomicInteger cfCounter = new AtomicInteger();
	public static Map<String, UserType> knownTypes = Maps.newHashMap();
	public static String cqlOverride = null;
	private static String FULL_BAR = Strings.repeat("█", 30);
	private static String EMPTY_BAR = Strings.repeat("░", 30);

	static {
		Config.setClientMode(true);
		
		// partitioner is not set in client mode.
		if (DatabaseDescriptor.getPartitioner() == null)
			DatabaseDescriptor.setPartitioner(Murmur3Partitioner.instance);
		
		// need system keyspace metadata registered for functions used in CQL like count/avg/json
		Schema.instance.setKeyspaceDefinition(SystemKeyspace.metadata());
	}
	
	public static CFMetaData tableFromBestSource(File sstablePath) {
		// TODO add CQL/Thrift mechanisms as well
		
		CFMetaData metadata;
		if (!Strings.isNullOrEmpty(cqlOverride)) {
			logger.debug("Using override metadata");
			metadata = CassandraUtils.tableFromCQL(new ByteArrayInputStream(cqlOverride.getBytes()));
		} else {
			InputStream in = findSchema();
			if (in == null) {
				logger.debug("Using metadata from sstable");
				metadata = CassandraUtils.tableFromSSTable(sstablePath);
			} else {
				metadata = CassandraUtils.tableFromCQL(in);
			}
		}
		return metadata;
	}
	
	private static Types getTypes() {
		if (knownTypes.isEmpty()) {
			return Types.none();
		} else {
			return Types.of(knownTypes.values().toArray(new UserTypes[0]));
		}
	}
	
	public static InputStream findSchema() {
		
	}
	
	public static void loadTablesFromRemote(String host, int port) {
		
	}
	
	public static <T> Stream<T> asStream(Iterator<T> iter) {
		Spliterator<T> splititer = Spliterators.spliteratorUnknownSize(iter, Spliterator.IMMUTABLE);
		return StreamSupport.stream(splititer, false);
	}
	
	private static class ValuedByteBuffer {
		public long value;
		public ByteBuffer buffer;
		
		public ValuedByteBuffer(ByteBuffer buffer, long value) {
			this.value = value;
			this.buffer = buffer;
		}
		
		public long getValue() {
			return value;
		}
	}
	
	private static Comparator<ValuedByteBuffer> VCOMP = Comparator.comparingLong(ValuedByteBuffer::getValue).reversed();
	
	public static void printStats(String fname, PrintStream out) {
		printStats(fname, out, null);
	}
	
	public static void printStats(String fname, PrintStream out, ConsoleReader console) {
		boolean color = console == null || console.getTerminal().isANSISupported();
		String c = color ? TableTransformer.ANSI_BLUE : "";
		String s = color ? TableTransformer.ANSI_CYAN : "";
		String r = color ? TableTransformer.ANSI_RESET : "";
		if (new File(fname).exists()) {
			Descriptor descriptor = Descriptor.fromFilename(fname);
			
			Map<MetadataType, MetadataComponent> metadata = descriptor.getMetadataSerializer().deserialize(descriptor, EnumSet.allOf(MetadataType.class));
			ValidationMetadata validation = (ValidationMetadata)metadata.get(MetadataType.VALIDATION);
			StatsMetadata stats = (StatsMetadata) metadata.get(MetadataType.STATS);
			CompactionMetadata compaction = (CompactionMetadata) metadata.get(MetadataType.COMPACTION);
			CompressionMetadata compression = null;
			File compressionFile = new File(descriptor.filenameFor(Component.COMPRESSION_INFO));
			if (compressionFile.exists())
				compression = CompressionMetadata.create(fname);
			SerializationHeader.Component header = (SerializationHeader.Component) metadata.get(MetadataType.HEADER);
			
			CFMetaData cfm = tableFromBestSource(new File(fname));
			SSTableReader reader = SSTableReader.openNoValidation(descriptor, cfm);
			ISSTableScanner scanner = reader.getScanner();
			long bytes = scanner.getLengthInBytes();
			MinMaxPriorityQueue<ValuedByteBuffer> widestPartitions = MinMaxPriorityQueue
					.orderedBy(VCOMP)
					.maximumSize(5)
					.create();
			MinMaxPriorityQueue<ValuedByteBuffer> largestPartitions = MinMaxPriorityQueue
					.orderedBy(VCOMP)
					.maximumSize(5)
					.create();
			MinMaxPriorityQueue<ValuedByteBuffer> mostTombstones = MinMaxPriorityQueue
					.orderedBy(VCOMP)
					.maximumSize(5)
					.create();
			long partitionCount = 0;
			long rowCount = 0;
			long tombstoneCount = 0;
			long cellCount = 0;
			double totalCells = stats.totalColumnsSet;
			int lastPercent = 0;
			while (scanner.hasNext()) {
				UnfilteredRowIterator partition = scanner.next();
				
				long psize = 0;
				long pcount = 0;
				int ptombcount = 0;
				partitionCount++;
				if (!partition.staticRow().isEmpty()) {
					rowCount++;
					pcount++;
					psize += partition.staticRow().dataSize();
				}
				if (!partition.partitionLevelDeletion().isLive()) {
					tombstoneCount++;
					ptombcount++;
				}
				while (partition.hasNext()) {
					Unfiltered unfiltered = partition.next();
					switch (unfiltered.kind()) {
					case ROW:
						rowCount++;
						Row row = (Row)unfiltered;
						psize += row.dataSize();
						pcount++;
						for (Cell cell : row.cells()) {
							cellCount++;
							double percentComplete = Math.min(1.0, cellCount / totalCells);
							if (lastPercent != (int) (percentComplete * 100)) {
								lastPercent = (int) (percentComplete * 100);
								int cols = (int) (percentComplete * 30);
								System.out.printf("\r%sAnalyzing SSTable... %s%s%s %s(%%%s)", c, s, FULL_BAR.substring(30 - cols), EMPTY_BAR.substring(cols), r, (int)(percentComplete * 100));
								System.out.flush();
							}
							if (cell.isTombstone()) {
								tombstoneCount++;
								ptombcount++;
							}
						}
						break;
					case RANGE_TOMBSTONE_MARKER:
						tombstoneCount++;
						ptombcount++;
						break;
					}
				}
				widestPartitions.add(new ValuedByteBuffer(partition.partitionKey().getKey(), pcount));
				largestPartitions.add(new ValuedByteBuffer(partition.partitionKey().getKey(), psize));
				mostTombstones.add(new ValuedByteBuffer(partition.partitionKey().getKey(), ptombcount));
			}
			out.printf("\r%80s\r", " ");
			out.printf("%sPartitions%s:%s%n %s%n", c, s, r, partitionCount);
			out.printf("%sRows%s:%s %s%n", c, s, r, rowCount);
			out.printf("%sTombstones%s:%s %s%n", c, s, r, tombstoneCount);
			out.printf("%sCells%s:%s %s%n", c, s, r, cellCount);
			out.printf("%sWidest Partitions%s:%s%n", c, s, r);
			asStream(widestPartitions.iterator()).sorted(VCOMP).forEach(p -> {
				if (p.value > 0) {
					out.printf("%s   [%s%s%s]%s %s%n", s, r, cfm.getKeyValidator().getString(p.buffer), s, r, p.value);
				}
			});
			
			List<AbstractType<?>> clusteringTypes = (List<AbstractType<?>>) readPrivate(header, "clusteringTypes");
			if (validation != null) {
				out.printf("%sPartitioner%s:%s %s%n", c, s, r, validation.partitioner);
				out.printf("%sBloom Filter FP chance%s:%s %f%n", c, s, r, validation.bloomFilterFPChance);				
			}
			if (stats != null) {
				out.printf("%sSize%s:%s %s %s %n", c, s, r, bytes, toByteString(bytes, true, color));
				out.printf("%sCompressor%s:%s %s%n", c, s, r, compression != null ? compression.compressor().getClass().getName() : "-");
				if (compression != null)
					out.printf("%s  Compression ratio%s:%s %s%n", c, s, r, stats.compressionRatio);
				
				out.printf("%sMinimum timestamp%s:%s %s %s%n", c, s, r, stats.minTimestamp, toDateString(stats.minTimestamp, TimeUnit.MICROSECONDS, color));
				out.printf("%sMaximum timestamp%s:%s %s %s%n", c, s, r, stats.maxTimestamp, toDateString(stats.maxTimestamp, TimeUnit.MICROSECONDS, color));
				
				out.printf("%sSSTable min local deletion time%s:%s %s %s%n", c, s, r, stats.minLocalDeletionTime, toDateString(stats.minLocalDeletionTime, TimeUnit.SECONDS, color));
				out.printf("%sSSTable max local deletion time%s:%s %s %s%n", c, s, r, stats.maxLocalDeletionTime, toDateString(stats.maxLocalDeletionTime, TimeUnit.SECONDS, color));
				
				out.printf("%sTTL min%s:%s %s %s%n", c, s, r, stats.minTTL, toDurationString(stats.minTTL, TimeUnit.SECONDS, color));
				out.printf("%sTTL max%s:%s %s %s%n", c, s, r, stats.maxTTL, toDurationString(stats.maxTTL, TimeUnit.SECONDS, color));
				if (header != null && clusteringTypes.size() == stats.minClusteringValues.size()) {
					List<ByteBuffer> minClusteringValues = stats.minClusteringValues;
					List<ByteBuffer> maxClusteringValues = stats.maxClusteringValues;
					String[] minValues = new String[clusteringTypes.size()];
					String[] maxValues = new String[clusteringTypes.size()];
					for (int i = 0; i < clusteringTypes.size(); i++) {
						minValues[i] = clusteringTypes.get(i).getString(minClusteringValues.get(i));
						maxValues[i] = clusteringTypes.get(i).getString(maxClusteringValues.get(i));
					}
					out.printf("%sminClustringValues%s:%s %s%n", c, s, r, Arrays.toString(minValues));
					out.printf("%smaxClustringValues%s:%s %s%n", c, s, r, Arrays.toString(maxValues));
				}
				out.printf("%sEstimated droppable tombstones%s:%s %s%n", c, s, r, stats.getEstimatedDroppableTombstoneRatio((int) (System.currentTimeMillis() / 1000)));
				out.printf("%sSSTable Level%s:%s %d%n", c, s, r, stats.sstableLevel);
				out.printf("%sRepaired at%s:%s %d %s%n", c, s, r, stats.repairedAt, toDateString(stats.repairedAt, TimeUnit.MILLISECONDS, color));
				out.printf("  %sLower bound%s:%s %s%n", c, s, r, stats.commitLogLowerBound);
				out.printf("  %sUpper bound%s:%s %s%n", c, s, r, stats.commitLogUpperBound);
				out.printf("%stotalColumnsSet%s:%s %s%n", c, s, r, stats.totalColumnsSet);
				out.printf("%stotalRows%s:%s %s%n", c, s, r, stats.totalRows);
				out.printf("%sEstimated tombstone drop times%s:%s%n", c, s, r);
				
				TermHistogram h = new TermHistogram(stats.estimatedTombstoneDropTime.getAsMap().entrySet());
				String bcolor = color ? "\u001B[36m" : "";
				String reset = color ? "\u001B[0m" : "";
				String histoColor = color ? "\u001B[37m" : "";
				out.printf("%s  %-" + h.maxValueLength + "s                       | %-" + h.maxCountLength + "s   %%   Histogram %n", bcolor, "Value", "Count");
				stats.estimatedTombstoneDropTime.getAsMap().entrySet().stream().forEach(e -> {
					String histo = h.asciiHistogram(e.getValue(), 30);
					out.printf(reset +
							"   %-" + h.maxValueLength + "d %s %s|%s %" + h.maxCountLength + "s %s %s%s %n",
							e.getKey().longValue(), toDateString(e.getKey().intValue(), TimeUnit.SECONDS, color),
							bcolor, reset,
							e.getValue(),
							wrapQuiet(String.format("%3s", (int) (100 * (e.getValue() / h.sum))), color),
							histoColor,
							histo);
				});
				
				out.printf("%sEstimated partition size%s:%s%n", c, s, r);
				TermHistogram.printHistogram(stats.estimatedPartitionSize, out, color);
				
				out.printf("%sEstimated column count%s:%s%n", c, s, r);
				TermHistogram.printHistogram(stats.estimatedColumnCount, out, color);				
			}
			if (compaction != null) {
				out.printf("%sEstimated cardinality%s:%s %s%n", c, s, r, compaction.cardinalityEstimator.cardinality());
			}
			if (header != null) {
                EncodingStats encodingStats = header.getEncodingStats();
                AbstractType<?> keyType = header.getKeyType();
                Map<ByteBuffer, AbstractType<?>> staticColumns = header.getStaticColumns();
                Map<ByteBuffer, AbstractType<?>> regularColumns = header.getRegularColumns();
                Map<String, String> statics = staticColumns.entrySet().stream()
                        .collect(Collectors.toMap(
                                e -> UTF8Type.instance.getString(e.getKey()),
                                e -> e.getValue().toString()));
                Map<String, String> regulars = regularColumns.entrySet().stream()
                        .collect(Collectors.toMap(
                                e -> UTF8Type.instance.getString(e.getKey()),
                                e -> e.getValue().toString()));

                out.printf("%sEncodingStats minTTL%s:%s %s %s%n", c, s, r, encodingStats.minTTL, toDurationString(encodingStats.minTTL, TimeUnit.SECONDS, color));
                out.printf("%sEncodingStats minLocalDeletionTime%s:%s %s %s%n", c, s, r, encodingStats.minLocalDeletionTime, toDateString(encodingStats.minLocalDeletionTime, TimeUnit.MILLISECONDS, color));
                out.printf("%sEncodingStats minTimestamp%s:%s %s %s%n", c, s, r, encodingStats.minTimestamp, toDateString(encodingStats.minTimestamp, TimeUnit.MICROSECONDS, color));
                out.printf("%sKeyType%s:%s %s%n", c, s, r, keyType.toString());
                out.printf("%sClusteringTypes%s:%s %s%n", c, s, r, clusteringTypes.toString());
                out.printf("%sStaticColumns%s:%s {%s}%n", c, s, r, FBUtilities.toString(statics));
                out.printf("%sRegularColumns%s:%s {%s}%n", c, s, r, FBUtilities.toString(regulars));
            }
		}
	}
	
}
