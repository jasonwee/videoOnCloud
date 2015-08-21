package org.just4fun.voc;

import org.just4fun.voc.storage.DataSource;
import org.just4fun.voc.storage.VirtualFileDAO;
import org.just4fun.voc.storage.cassandra.CassandraDAO;
import org.just4fun.voc.storage.elasticsearch.ElasticsearchDAO;
import org.just4fun.voc.storage.hbase.HbaseDAO;

public class ManagerDAO {
	
	private DataSource dataSource = null;
	
	protected VirtualFileDAO virtualFile = null;
	
	public ManagerDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public VirtualFileDAO getVirtualFileDAO() throws Exception {
		switch (dataSource.getName()) {
		case CASSANDRA:
			if (virtualFile == null) {
				virtualFile = new CassandraDAO();
			}
			break;
		case ELASTICSEARCH:
			if (virtualFile == null) {
				virtualFile = new ElasticsearchDAO();
			}
			break;
		case HBASE:
			if (virtualFile == null) {
				virtualFile = new HbaseDAO();
			}
			break;
		default:
			throw new Exception("crap lorrr");
		}
		return virtualFile;
	}

}
