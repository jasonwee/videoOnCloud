package org.just4fun.voc.storage;

public class DataSource {
	
	public enum SOURCES {
		CASSANDRA,
		ELASTICSEARCH,
		HBASE,
	}
	
	private SOURCES name;
	
	public DataSource(SOURCES name) {
		this.name = name;
	}
	
	public SOURCES getName() {
		return name;
	}

}
