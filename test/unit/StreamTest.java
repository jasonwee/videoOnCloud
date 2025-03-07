import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import me.prettyprint.cassandra.connection.HConnectionManager;
import me.prettyprint.cassandra.io.ChunkInputStream;
import me.prettyprint.cassandra.io.ChunkOutputStream;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ThriftCluster;
import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;

import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.KsDef;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class StreamTest {
	
	private final static String STRING_TEST_DATA = "This is a testdata, we should be able to read it again via the Inpustream - éèëàâ";
	private final static byte[] BINARY_TEST_DATA = { 0x01, 0x00, 0x0a, 0x03, 0x0d, 0x0a, 0x04, (byte) 0xff, 0x05 };
	
	public final static String POOL_NAME = "TestPool";
	public final static String KEYSPACE = "TestKeyspace";

	public final static String BLOB_CF = "Blob";
	public final static CfDef BLOB_CF_DEF = new CfDef(KEYSPACE, BLOB_CF);
	static {
		BLOB_CF_DEF.comparator_type = "IntegerType";
	}

	public static KeyspaceDefinition KEYSPACE_DEV;

	private Keyspace keyspace;
	private ThriftCluster cassandraCluster;
	private CassandraHostConfigurator cassandraHostConfigurator;
	protected HConnectionManager connectionManager;
	protected String clusterName = "just4fun";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	    cassandraHostConfigurator = new CassandraHostConfigurator("192.168.0.2:9160");
	    connectionManager = new HConnectionManager(clusterName,cassandraHostConfigurator);
	    
	    KEYSPACE_DEV = new ThriftKsDef(new KsDef(KEYSPACE, "org.apache.cassandra.locator.SimpleStrategy", Arrays.asList(new CfDef[] { BLOB_CF_DEF })));
	    ((ThriftKsDef)KEYSPACE_DEV).setReplicationFactor(1);
	    cassandraCluster = new ThriftCluster("just4fun", cassandraHostConfigurator);
	    
	    keyspace = HFactory.createKeyspace(KEYSPACE, cassandraCluster);
	    try {
	      cassandraCluster.dropKeyspace(KEYSPACE); 
	    } catch (Exception e) {
	    	
	    }
	    cassandraCluster.addKeyspace(KEYSPACE_DEV);

	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testStreaming() throws IOException {
		String key1 = UUID.randomUUID().toString();
		String key2 = UUID.randomUUID().toString();
		check(key1, 10);
		check(key2, 10);
		check(key2, 10000);
		check(key1, BINARY_TEST_DATA, 10);
	}

	private void check(String key, int chunksize) throws IOException {
		check(key, STRING_TEST_DATA.getBytes(), chunksize);
	}
	
	private void check(String key, byte[] value, int chunksize)
			throws IOException {
		ChunkOutputStream<String> out = new ChunkOutputStream<String>(keyspace,
				BLOB_CF, key, StringSerializer.get(), chunksize);
		out.write(value);
		out.close();

		ChunkInputStream<String> in = new ChunkInputStream<String>(keyspace,
				BLOB_CF, key, StringSerializer.get());
		int i = -1;
		int written = 0;

		while ((i = in.read()) != -1) {
			assertSame(value[written++], (byte) i);
		}

		assertEquals(value.length, written);
		System.out.println("written " + written );
		in.close();
	}

	@Test
	public void testSkip() throws IOException {
		String key = UUID.randomUUID().toString();
		String testData = "This is a testdata, we should be able to read it again via the Inpustream";
		ChunkOutputStream<String> out = new ChunkOutputStream<String>(keyspace,
				BLOB_CF, key, StringSerializer.get(), 10);
		out.write(testData.getBytes());
		out.close();

		ChunkInputStream<String> in = new ChunkInputStream<String>(keyspace,
				BLOB_CF, key, StringSerializer.get());

		int skip = 5;
		in.skip(skip);
		int i = -1;

		int written = skip;

		while ((i = in.read()) != -1) {
			assertSame(testData.charAt(written++), (char) i);

		}

		assertEquals(testData.length(), written);
		in.close();

	}

}
