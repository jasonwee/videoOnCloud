package org.just4fun.voc.storage.cassandra;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.cassandra.utils.ByteBufferUtil;
import org.just4fun.voc.file.BinaryFile;
import org.just4fun.voc.storage.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ColumnSliceIterator;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.SliceQuery;

// https://github.com/zznate/hector-examples
// https://github.com/zznate/cassandra-tutorial
// http://hector-client.github.io/hector/build/html/index.html
public class ConnectionHector implements Connection {
	
	Cluster j4fCluster = null;
	Keyspace ksp = null;
	ColumnFamilyTemplate<String, ByteBuffer> template = null;
	
	Logger logger = LoggerFactory.getLogger(ConnectionHector.class);
	
	private String columnFamily = null;
	private String keyspace = null;
	private String clusterName = null;
	private int blockSize;
	
	public ConnectionHector(CassandraClientConfiguration config) {
		if (config == null) throw new IllegalArgumentException("config must not null");
		config.checkRequired();
		this.clusterName = config.getClusterName();
		this.keyspace = config.getKeyspace();
		this.columnFamily = config.getColumnFamily();
		this.blockSize = config.getBlockSize();
		
		CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator();
		cassandraHostConfigurator.setHosts(config.getHosts());
		j4fCluster = HFactory.getOrCreateCluster(this.clusterName, cassandraHostConfigurator);
		
		maybeCreateSchema();
		
		ksp = HFactory.createKeyspace(this.keyspace, j4fCluster);
		template = new ThriftColumnFamilyTemplate<>(ksp, this.columnFamily, StringSerializer.get(), ByteBufferSerializer.get());
	}
	
	private void maybeCreateSchema() {
		KeyspaceDefinition keyspaceDef = j4fCluster.describeKeyspace(this.keyspace);
		if (keyspaceDef == null) {
			KeyspaceDefinition newKeyspace = HFactory.createKeyspaceDefinition(this.keyspace);
			j4fCluster.addKeyspace(newKeyspace, true);
		}
		
		boolean createCF = true;
		List<ColumnFamilyDefinition> columnFamilies = keyspaceDef.getCfDefs();
		Iterator<ColumnFamilyDefinition> iter = columnFamilies.iterator();
		while (iter.hasNext()) {
			ColumnFamilyDefinition cf = iter.next();
			if (cf.getName().equals(this.columnFamily)) {
				createCF = false;
			}
		}
		if (createCF) {
			ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition(this.keyspace, this.columnFamily, ComparatorType.BYTESTYPE);
			j4fCluster.addColumnFamily(cfDef);
		}
	}
	
	public void update(String key, String columnName, String columnValue) {
		ColumnFamilyUpdater<String, ByteBuffer> updater = template.createUpdater(key);
		ByteBuffer bbName = ByteBuffer.wrap(columnName.getBytes());
		ByteBuffer bbValue = ByteBuffer.wrap(columnValue.getBytes());
		updater.setByteBuffer(bbName, bbValue);
		
		template.update(updater);
	}
	
	public void update(String key, byte[] columnName, byte[] columnValue) {
		ColumnFamilyUpdater<String, ByteBuffer> updater = template.createUpdater(key);
		ByteBuffer bbName = ByteBuffer.wrap(columnName);
		updater.setByteArray(bbName, columnValue);
		
		template.update(updater);
	}
	
	public String read(String key, String columnName) {
		ColumnFamilyResult<String, ByteBuffer> res = template.queryColumns(key);
		ByteBuffer bbName = ByteBuffer.wrap(columnName.getBytes());
		String value = res.getString(bbName);
		return value;
	}
	
	public void delete(String key, String columnName) {
		ByteBuffer bbName = ByteBuffer.wrap(columnName.getBytes());
		template.deleteColumn(key, bbName);
	}
	
	public void delete(String key) {
		template.deleteRow(key);
	}
	
	public Map<ByteBuffer, ByteBuffer> getColumns(String key) {
		SliceQuery<String, ByteBuffer, ByteBuffer> query = HFactory.createSliceQuery(ksp, StringSerializer.get(),
				ByteBufferSerializer.get(), ByteBufferSerializer.get());
		query.setKey(key);
		query.setColumnFamily(this.columnFamily);

		ColumnSliceIterator<String, ByteBuffer, ByteBuffer> iterator = new ColumnSliceIterator<String, ByteBuffer, ByteBuffer>(query, null, ByteBuffer.wrap("\uFFFF".getBytes()), false);
		
		Map<ByteBuffer, ByteBuffer> columns = new LinkedHashMap<ByteBuffer, ByteBuffer>();
		while (iterator.hasNext()) {
			HColumn<ByteBuffer, ByteBuffer> column = iterator.next();
			columns.put(column.getName(), column.getValue());
		}
		return columns;
	}
	
	public Map<ByteBuffer, byte[]> getColumns(String key, List<ByteBuffer> columns) {
		SliceQuery<String, ByteBuffer, ByteBuffer> query = HFactory.createSliceQuery(ksp, StringSerializer.get(),
				ByteBufferSerializer.get(), ByteBufferSerializer.get());
		query.setKey(key);
		query.setColumnFamily(this.columnFamily);

		ColumnSliceIterator<String, ByteBuffer, ByteBuffer> iterator = new ColumnSliceIterator<String, ByteBuffer, ByteBuffer>(query, null, ByteBuffer.wrap("\uFFFF".getBytes()), false);
		
		Map<ByteBuffer, byte[]> result = new LinkedHashMap<ByteBuffer, byte[]>();
		while (iterator.hasNext()) {
			HColumn<ByteBuffer, ByteBuffer> column = iterator.next();
			result.put(column.getName(), ByteBufferUtil.getArray(column.getValue()));
			
			//logger.info("key {}", BinaryFile.toHex(ByteBufferUtil.getArray(column.getValue())));
			
		}
		for (ByteBuffer bbColumn : columns) {
			byte[] bValue = result.get(bbColumn);
			result.put(bbColumn, bValue);
		}
		return result;
	}
	
	@Override
	public boolean storeBinary(String name, BinaryFile binary) throws IOException {
		
		logger.info("saving " + name);
		byte[] content = BinaryFile.toByteArray(binary.getHexContent());
		System.out.println(content.length);
		write(name, content, 0, content.length);
		logger.info("saved " + name);
		
		return true;
	}
	
	private void write(String name, byte[] b, int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException("array b is null");
        }
        
        if (off < 0 ) {
            throw new IndexOutOfBoundsException("offset for the file which is going to be written must not be negative");
        }
        if (len < 0) {
            throw new IndexOutOfBoundsException("byte array length must not be negative");
        }
        if (len > b.length - off) {
            throw new IndexOutOfBoundsException("write length must not greater than buffer length minus buffer offset");
        }
        
        if (len == 0) {
            logger.trace("len is 0 , nothing to flush");
            return;
        }

        BlockMap blocksToFlush = new BlockMap();

        FileDescriptor fd = new FileDescriptor(name, blockSize);

        int bytesLeftToWrite = len;
        int bytesAddedByWrite = 0;
        FileBlock currentBlock = fd.getFirstBlock();
        String debug = "";
        
        do {
        	int dataLength = (int) Math.min(currentBlock.getBlockSize() - currentBlock.getPositionOffset(), bytesLeftToWrite);
        	
            FileBlock nextBlock;
            
            currentBlock.setDataLength(dataLength);
            currentBlock.setDataOffset(currentBlock.getPositionOffset());
            if (logger.isDebugEnabled()) {
            	debug = String.format("setting block %s for file %s with dataoffset %s", currentBlock.getBlockName(), name, currentBlock.getPositionOffset());
                logger.trace(debug);
            }
            
            nextBlock = fd.createBlock();
            
            byte[] partialBytes = new byte[dataLength];
            System.arraycopy(b, off, partialBytes, 0, dataLength);
            blocksToFlush.put(currentBlock.getBlockName(), partialBytes);
            fd.insertBlock(currentBlock, nextBlock, true);
            
            bytesLeftToWrite -= dataLength;
            off += dataLength;
            
            if (bytesLeftToWrite <= 0 ) {
            	fd.removeBlock(nextBlock);
            }

            currentBlock = nextBlock;
            if (logger.isDebugEnabled()) {
            	logger.debug("bytesLeftToWrite {} ", bytesLeftToWrite);
            }

        } while (bytesLeftToWrite > 0) ;
        
        fd.setLength(bytesAddedByWrite);
        flush(fd, blocksToFlush);
        if (logger.isDebugEnabled()) {
        	if (blocksToFlush.size() > 1) {
                for (Entry<byte[], byte[]> entry  : blocksToFlush.entrySet()) {
                    logger.debug("flushing block {} ", new String(entry.getKey()));
                }
            }
            logger.debug("file descriptor " + FileDescriptorUtils.toString(fd));
        }
        
        if (bytesLeftToWrite > 0) {
            logger.error("did not write fully as expected, remaining {}", bytesLeftToWrite);
        }
        
	}
	
	private void flush(FileDescriptor fd, BlockMap blocksToFlush) throws IOException {
		try {
			for (Entry<byte[], byte[]> columns : blocksToFlush.entrySet()) {
				update(fd.getName(), columns.getKey(), columns.getValue());
			}
			update(fd.getName(), ColumnOrientedFile.descriptorColumn, FileDescriptorUtils.toString(fd));
		} catch (Exception e) {
			throw new IOException("unable to flush");
		}
		
	}
	
	@Override
	public boolean deleteBinary(String name) {
		logger.info("deleting {}", name);
		delete(name);
		logger.info("deleted {}", name);
		return true;
	}
	
	@Override
	public BinaryFile readBinary(String name) throws IOException {
		String json = read(name, ColumnOrientedFile.descriptorColumn);
		if (json == null) {
			throw new IOException("file not found");
		}
		FileDescriptor fileDescriptor = FileDescriptorUtils.fromBytes(json.getBytes(), blockSize);
		
		if (fileDescriptor == null) {
			throw new IOException("invalid file descriptor.");
		}
		
		List<FileBlock> fileBlocks = fileDescriptor.getBlocks();
		List<ByteBuffer> blockNamesOnly = new ArrayList<ByteBuffer>();
		for (FileBlock fb : fileBlocks) {
			blockNamesOnly.add(ByteBuffer.wrap(fb.getBlockName().getBytes()));
		}
		
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		logger.info("size {}", fileBlocks.size());
		Map<ByteBuffer, byte[]> bbFile = getColumns(name, blockNamesOnly);
		logger.info("size {}", bbFile.size());
		for (Entry<ByteBuffer, byte[]> value : bbFile.entrySet()) {
			logger.info("key {} value {}", ByteBufferUtil.string(value.getKey()), BinaryFile.toHex(value.getValue()));
		}
		for (int i = 0; i < fileBlocks.size(); i++) {
			//ByteBuffer bbValue = bbFile.get(FileBlock.BLOCK_COLUMN_NAME_PREFIX + i);
			ByteBuffer blockName = ByteBuffer.wrap((FileBlock.BLOCK_COLUMN_NAME_PREFIX + i).getBytes());
			//byte[] bytes = new byte[bbValue.capacity()];
			if (bbFile == null) logger.info("bbFile is null") ;
			if (bOut == null) logger.info("bOut is null") ;
			if (bbFile.get(blockName) == null) logger.info("get is null") ;
			if (bbFile.get(blockName).length == -1) logger.info("length is null") ;
			bOut.write(bbFile.get(blockName), 0, bbFile.get(blockName).length);
		}
		bOut.flush();
			
		BinaryFile file = new BinaryFile(null, name, fileDescriptor.getLength(), fileDescriptor.getLastModified(), fileDescriptor.getLastAccessed());
		file.setHexContent(BinaryFile.toHex(bOut.toByteArray()));
		return file;
	}

	public static void main(String[] args) throws CharacterCodingException {
		CassandraClientConfiguration config = new CassandraClientConfiguration();
		ConnectionHector lh = new ConnectionHector(config);
		lh.update("123", "456", "789");
		System.out.println("read => " + lh.read("123", "456"));
		Map<ByteBuffer, ByteBuffer> result = lh.getColumns("123");
		for (Entry<ByteBuffer, ByteBuffer> column : result.entrySet()) {
			String key = ByteBufferUtil.string(column.getKey(), StandardCharsets.UTF_8);
			String value = ByteBufferUtil.string(column.getValue(), StandardCharsets.UTF_8);
			System.out.println(key + " " + value );
		}
		lh.delete("123", "456");
	}



}
