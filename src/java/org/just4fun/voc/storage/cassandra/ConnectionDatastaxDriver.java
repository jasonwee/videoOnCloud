package org.just4fun.voc.storage.cassandra;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.RoundRobinPolicy;
import com.datastax.driver.core.policies.WhiteListPolicy;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Update;

import static com.datastax.driver.core.querybuilder.QueryBuilder.*;

public class ConnectionDatastaxDriver implements Connection {
	
	private Cluster cluster;
	private Session session;
	private QueryBuilder queryBuilder;
	
	private String keyspace = null;
	private String columnFamily = null;
	private String filename = null;
	private int blockSize;
	
	Logger logger = LoggerFactory.getLogger(ConnectionDatastaxDriver.class);
	
	public ConnectionDatastaxDriver(CassandraClientConfiguration config) {
		if (config == null) throw new IllegalArgumentException("config must not null");
		config.checkRequired();
		this.keyspace = config.getKeyspace();
		this.columnFamily = config.getColumnFamily();
		this.blockSize = config.getBlockSize();
		
		List<InetSocketAddress> whiteList = Arrays.asList(new InetSocketAddress("2001:e68:5424:ef50:224:1ff:fed7:82ea", 9042));
		WhiteListPolicy wlp = new WhiteListPolicy(new RoundRobinPolicy(), whiteList);
		cluster = Cluster.builder().addContactPoint("2001:e68:5424:ef50:224:1ff:fed7:82ea").withLoadBalancingPolicy(wlp).build();
		
		if (logger.isDebugEnabled()) {
			Metadata metadata = cluster.getMetadata();
			logger.info("Connected to cluster: %s \n", metadata.getClusterName());
			
			for (Host host : metadata.getAllHosts()) {
				logger.info(String.format("Datacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(), host.getAddress(), host.getRack()));
			}
		}
		session = cluster.connect();
		queryBuilder = new QueryBuilder(cluster);
		
		maybeCreateSchema();
	}
	
	public void close() {
		cluster.close();
	}
	
	public void maybeCreateSchema() {
		String statement = null;
				
		statement = String.format("select count(1) as count from system_schema.keyspaces where keyspace_name = '%s';", keyspace);
		ResultSet rs = session.execute(statement);
		List<Row> rows = rs.all();
		if (rows.size() != 1) {
			logger.error("count at least return the count.");
			throw new RuntimeException("count at least return the count.");
		}
		
		if (rows.get(0).getLong(0) == 0) {
			statement = String.format("CREATE KEYSPACE %s WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '3'};", keyspace);
			session.execute(statement);
		}
		
		statement = String.format("select count(1) from system_schema.tables where keyspace_name = '%s' and 	table_name = '%s';", keyspace, columnFamily);
		rs = session.execute(statement);
		rows = rs.all();
		if (rows.size() != 1) {
			logger.error("count at least return the count.");
			throw new RuntimeException("count at least return the count.");
		}
		
		if (rows.get(0).getLong(0) == 0) {
			statement = String.format("CREATE TABLE %s.%s ( key blob, column1 blob, value blob, PRIMARY KEY (key, column1));", keyspace, columnFamily);
			session.execute(statement);
		}
	}
	
	// CRUD
	
	public void update(String key, String columnName, String columnValue) {
		
		Update update = queryBuilder.update(keyspace, columnFamily);
		update.with(set("value", columnValue));
		update.where(eq("key", key)).and(eq("column1", columnName));
		session.execute(update);
	}
	
	public void update(String key, byte[] columnName, byte[] columnValue) {
		Update update = queryBuilder.update(keyspace, columnFamily);
		update.with(set("value", ByteBuffer.wrap(columnValue)));
		update.where(eq("key", key)).and(eq("column1", ByteBuffer.wrap(columnName)));
		session.execute(update);
	}
	
	public void insert(String key, String columnName, String columnValue) {
		String insert = String.format("INSERT INTO %s.%s (key, column1, value) VALUES (?, ?, ?);", keyspace,columnFamily);
		PreparedStatement statement = session.prepare(insert);
		BoundStatement boundStatement = new BoundStatement(statement);
		
		session.execute(boundStatement.bind(ByteBufferUtil.bytes(key), 
				ByteBufferUtil.bytes(columnName), ByteBufferUtil.bytes(columnValue)));
	}
	
	public void delete(String key, String columnName) {
		Delete delete = queryBuilder.delete().from(keyspace, columnFamily);
		delete.where(eq("key", key)).and(eq("column1",columnName));
		session.execute(delete);
	}
	
	public void delete(String key) {
		Delete delete = queryBuilder.delete().from(keyspace, columnFamily);
		delete.where(eq("key", key));
		session.execute(delete);
	}
	
	public Map<ByteBuffer, ByteBuffer> getColumns(String key) {
		String select = String.format("SELECT column1, value from %s.%s WHERE key = ?;", keyspace, columnFamily);
		PreparedStatement statement = session.prepare(select);
		BoundStatement boundStatement = new BoundStatement(statement);
		
		ResultSet rs = session.execute(boundStatement.bind(ByteBufferUtil.bytes(key)));
		Iterator<Row> iter = rs.iterator();
		
		Map<ByteBuffer, ByteBuffer> result = new LinkedHashMap<ByteBuffer, ByteBuffer>();
		
		while (iter.hasNext()) {
			Row row = iter.next();
			result.put(row.getBytes("column1"), row.getBytes("value"));
		}
		
		return result;
	}
	
	public Map<ByteBuffer, byte[]> getColumns(String key, List<ByteBuffer> columns) {
		String select = String.format("SELECT column1, value from %s.%s WHERE key = ?;", keyspace, columnFamily);
		PreparedStatement statement = session.prepare(select);
		BoundStatement boundStatement = new BoundStatement(statement);
		
		ResultSet rs = session.execute(boundStatement.bind(ByteBufferUtil.bytes(key)));
		List<Row> rows = rs.all();
		
		Map<ByteBuffer, byte[]> result = new LinkedHashMap<ByteBuffer, byte[]>();
		
		for (Row row : rows) {
			ByteBuffer columnName = row.getBytes("column1");
			if (columns.contains(columnName)) {
				result.put(columnName, ByteBufferUtil.getArray(row.getBytes("value")));
			}
		}
		
		return result;
	}
	
	public String getColumn(String key, String columnName) {
		List<ByteBuffer> column = new ArrayList<>();
		column.add(ByteBufferUtil.bytes(columnName));
		
		Map<ByteBuffer, byte[]> res = getColumns(key, column);
		ByteBuffer bbName = ByteBufferUtil.bytes(columnName);
		if (res.get(bbName) == null) {
			return null;
		}
		
		byte[] value = res.get(bbName);
		return new String(value);
	}
	
	// CRUD

	@Override
	public boolean storeBinary(String name, BinaryFile file) throws IOException {
		logger.info("saving " + name);
		byte[] content = BinaryFile.toByteArray(file.getHexContent());
		write(name, content, 0, content.length);
		logger.info("saved " + name);
		return true;
	}
	
	private void write(String name, byte[] b, int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException("source array b canot be null");
        }
        
        if (off < 0 ) {
            throw new IndexOutOfBoundsException("offset for the file which is going to be written must not be negative");
        }
        if (len < 0) {
            throw new IndexOutOfBoundsException("source byte array length must not be negative");
        }
        if (len > b.length - off) {
            throw new IndexOutOfBoundsException("write length must not greater than buffer length minus buffer offset");
        }

        if (name == null || name.isEmpty()) {
        	throw new NullPointerException("destination name must not be null or empty");        	
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
		String json = getColumn(name, ColumnOrientedFile.descriptorColumn);
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

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		if (len == 0) {
			return;
		}

		if (b == null) {
			throw new NullPointerException("byte array cannot be null");
		}

		if (off < 0 || len < 0 || off + len > b.length)  {
			throw new IndexOutOfBoundsException("off or len cannot be negative or off plus len cannot be more than b.length.");
		}
		
		if (filename == null) {
			throw new NullPointerException("filename must be specified.");
		}
		
		write(filename, b, off, len);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if (len == 0) {
			return 0;
		}

		if (b == null) {
			throw new NullPointerException("byte array cannot be null");
		}

		if (off < 0 || len < 0 || len > b.length - off)  {
			throw new IndexOutOfBoundsException("off or len cannot be negative and len cannot be more than b.length minus off");
		}
		
		if (filename == null) {
			throw new NullPointerException("filename must be specified.");
		}

		int total_bytes = 0;

		long bytesRead = len;
		int blockNumber = 0;
		int offset = off;
		
		do {
			logger.info(String.format("before blockNumber = '%s' bytesRead = '%s' offset = '%s'", blockNumber, bytesRead, offset));
			
			String blockName = FileBlock.BLOCK_COLUMN_NAME_PREFIX + String.valueOf(blockNumber);
			ByteBuffer bb = ByteBufferUtil.bytes(blockName);
			List<ByteBuffer> column = new ArrayList<ByteBuffer>();
			byte[] bytes = getColumns(filename, column).get(bb);
			
			if (bytes == null) {
				break;
			}
			System.arraycopy(bytes, 0, b, offset, bytes.length);
			
			bytesRead -= bytes.length;
			blockNumber++;
			offset += bytes.length;
			
			logger.info(String.format("after blockNumber = '%s' bytesRead = '%s' offset = '%s'", blockNumber, bytesRead, offset));
			
		} while (bytesRead >= 0);
			
		if (total_bytes == 0) {
			return -1;
		}
		
		return total_bytes;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public static void main(String[] args) throws IOException {
		CassandraClientConfiguration config = new CassandraClientConfiguration();
		ConnectionDatastaxDriver client = new ConnectionDatastaxDriver(config);
		client.insert("filename.txt", "BLOCK-0", "hello world 0");
		client.insert("filename.txt", "BLOCK-1", "hello world 1");
		client.insert("filename.txt", "BLOCK-2", "hello world 2");
		client.insert("filename.txt", "BLOCK-3", "hello world 3");
		client.insert("filename.txt", "BLOCK-4", "hello world 4");
		client.insert("filename.txt", "BLOCK-5", "hello world 5");
		
		client.update("filename.txt", "BLOCK-0", "123");
		
		byte[] columnName = "BLOCK-0".getBytes();
		byte[] columnValue = "456".getBytes();
		client.update("filename.txt", columnName, columnValue);
		
		Map<ByteBuffer, ByteBuffer> result = client.getColumns("filename.txt");
		System.out.println("result size " + result.size());
		
		List<ByteBuffer> columns = new ArrayList<ByteBuffer>();
		columns.add(ByteBufferUtil.bytes("BLOCK-2"));
		columns.add(ByteBufferUtil.bytes("BLOCK-3"));
		columns.add(ByteBufferUtil.bytes("BLOCK-4"));
		
		Map<ByteBuffer, byte[]> results = client.getColumns("filename.txt", columns);
		System.out.println("results size " + results.size());
		for (Entry<ByteBuffer, byte[]> r : results.entrySet()) {
			System.out.printf("key = '%s' value = '%s' %n", ByteBufferUtil.string(r.getKey()), new String(r.getValue()));
		}
		
		BinaryFile bf = BinaryFile.getBinaryFile("/", "django.mp3", new File("./src/resources/django.mp3"));
		client.storeBinary("django.mp3", bf);
		
		BinaryFile bfOut = client.readBinary("django.mp3");
		BinaryFile.toFile(bfOut.getHexContent(), "/home/jason/Desktop/django-new.mp3");

		client.delete("django.mp3");
		
		byte[] b = "hello world".getBytes();
		
		client.setFilename("newFile.txt");
		client.write(b, 0, b.length);
		
		byte[] c = new byte[20]; 
		client.read(c, 0, 10);
		
		System.out.println("c byte array " + new String(c));
		
		
		
		client.delete("filename.txt", "BLOCK-0");
		
		client.delete("filename.txt");
		
		
		//client.loadData();
		//client.querySchema();
		client.close();
	}

}
