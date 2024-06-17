import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

//import org.apache.cassandra.db.Column;
//import org.apache.cassandra.db.ColumnFamily;
import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.db.DecoratedKey;
import org.apache.cassandra.db.Directories;
//import org.apache.cassandra.db.Row;
//import org.apache.cassandra.db.RowMutation;
//import org.apache.cassandra.db.RowPosition;
//import org.apache.cassandra.db.Table;
//import org.apache.cassandra.db.columniterator.IdentityQueryFilter;
import org.apache.cassandra.db.compaction.CompactionManager;
import org.apache.cassandra.dht.IPartitioner;
import org.apache.cassandra.dht.Range;
import org.apache.cassandra.io.util.FileUtils;
import org.apache.cassandra.service.StorageService;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.cassandra.utils.CLibrary;



// cassandra 1.2
public class TestCassandraCorruptSSTable {
	
	/*
    private static RowPosition rp(String key, IPartitioner partitioner) {
        return RowPosition.forKey(ByteBufferUtil.bytes(key), partitioner);
    }
	
    private static RowPosition rp(String key) {
        return rp(key, StorageService.getPartitioner());
    }
	
	private static Column column(String name, String value, long timestamp) {
        return new Column(ByteBufferUtil.bytes(name), ByteBufferUtil.bytes(value), timestamp);
    }
	
	private static Range<RowPosition> range(String left, String right) {
		return new Range<RowPosition>(rp(left), rp(right));
	}
	
	private void fillCF(ColumnFamilyStore cfs, int rowsPerSSTable) throws ExecutionException, InterruptedException {
		for (int i = 0; i < rowsPerSSTable; i++) {
			String key = String.valueOf(i);
			// create a row and update the birthdate value, test that the index query fetches
			// the new version
			RowMutation rm;
			rm = new RowMutation("Keyspace1", ByteBufferUtil.bytes(key));
			ColumnFamily cf = ColumnFamily.create("Keyspace1", "Standard1");
			cf.addColumn(column("c1", "1", 1L));
			cf.addColumn(column("c2", "2", 1L));
			rm.add(cf);
			rm.applyUnsafe();
		}
		cfs.forceBlockingFlush();
	}
	
	public void scrubOneRow() throws InterruptedException, ExecutionException {
		CompactionManager.instance.disableAutoCompaction();
		Table table = Table.open("Keyspace1");
		ColumnFamilyStore cfs = table.getColumnFamilyStore("Standard1");
		cfs.clearUnsafe();
		
		List<Row> rows;
		
		// insert data and verify we get it back w/ range query
		fillCF(cfs, 1);
		rows = cfs.getRangeSlice(null, range("", ""), 1000, new IdentityQueryFilter(), null);
		System.out.println("row size='" + rows.size() + "' // should be 1"  );
		
		CompactionManager.instance.performScrub(cfs);
		
		// check data is still there
		rows = cfs.getRangeSlice(null, range("", ""), 1000, new IdentityQueryFilter(), null);
		System.out.println("row size='" + rows.size() + "' // should be 1"  );
	}
	
	// -------------------------- //
	
	private static boolean isRowOrdered(List<Row> rows) {
		DecoratedKey prev = null;
		for (Row row : rows) {
			if (prev != null && prev.compareTo(row.key) > 0)
				return false;
			prev = row.key;
		}
		return true;
	}
	
	public void scrubOutOfOrder() throws IOException, InterruptedException, ExecutionException {
		CompactionManager.instance.disableAutoCompaction();
		Table table = Table.open("Keyspace1");
		String columnFamily = "Standard3";
		ColumnFamilyStore cfs = table.getColumnFamilyStore(columnFamily);
		cfs.clearUnsafe();
		
		// ~>
		String root = "change me to a directory that contain corrupted sstables";
		File rootDir = new File(root);
		System.out.println(String.valueOf(rootDir.isDirectory()) + " rootDir must be a directory");
		
		File destDir = Directories.create("Keyspace1",  "Standard3").getDirectoryForNewSSTables();
		
		String corruptSSTableName = null;
		
		FileUtils.createDirectory(destDir);
		for (File srcFile: rootDir.listFiles()) {
			if (srcFile.getName().equals(".svn"))
				continue;
			if (!srcFile.getName().contains( "Standard3"))
				continue;
			File destFile = new File(destDir, srcFile.getName());
			CLibrary.createHardLink(srcFile, destFile);
			
			assert destFile.exists() : destFile.getAbsoluteFile();
			
			if (destFile.getName().endsWith("Data.db"))
				corruptSSTableName = destFile.getCanonicalPath();
		}
		
		assert corruptSSTableName != null;
		// ~>
		
		cfs.loadNewSSTables();
		System.out.println(String.valueOf(cfs.getSSTables().size() > 0) + " must be greater than 0");
		
		List<Row> rows;
		rows = cfs.getRangeSlice(null, range("", ""), 1000, new IdentityQueryFilter(), null);
		assert !isRowOrdered(rows) : "'corrupt' test file actually was not";
		
		CompactionManager.instance.performScrub(cfs);
		rows = cfs.getRangeSlice(null, range("", ""), 1000, new IdentityQueryFilter(), null);
		assert isRowOrdered(rows) : "Scrub failed: " + rows;
		assert rows.size() == 6 : "Got " + rows.size();		
		
	}
	*/

	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
		TestCassandraCorruptSSTable tester = new TestCassandraCorruptSSTable();
		//tester.scrubOneRow();
		//tester.scrubOutOfOrder();
	}

}
