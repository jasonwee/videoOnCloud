package org.just4fun.voc.storage.cassandra;

/**
 * The <code>ColumnOrientedFile</code> captures the mapping of the concept
 * of a file to a row in Cassandra. Specifically, it considers each column
 * in the row as a block in the file. Furthermore, it uses one of the
 * columns to hold the {@link FileDescriptor} for the file, in the form of a
 * JSON string (which serves to make the "file" readable by other,
 * potentially disparate, clients).
 * 
 * <p>
 * This class in turn relies on the {@link CassandraClient} for all
 * low-level gets and puts to the Cassandra server. More importantly, it
 * does not require that the {@link CassandraClient} be familiar with the
 * notion of Lucene files. Rather, it transparently translates those notions
 * to rows within the column family denoting the directory. In so doing, it
 * ends up hiding the Cassandra layer from its consumers.
 * </p>
 */
public class ColumnOrientedFile {
    
    // The name of the column that holds the file descriptor.
    protected static final String descriptorColumn = "DESCRIPTOR";
    
    public ColumnOrientedFile() {
    	
    }

}
