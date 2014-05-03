package org.just4fun.voc.storage.cassandra;

import java.util.ArrayList;
import java.util.List;

public class CassandraClientConfiguration {

	private List<String> hosts = new ArrayList<String>();

	private String clusterName = null;

	private String keyspace = null;

	private String columnFamily = null;

	private int blockSize;

	public CassandraClientConfiguration() {
		this.clusterName = "just4fun";
		this.keyspace = "jw_schema1";
		this.columnFamily = "video";
		this.hosts.add("192.168.0.2:9160");
		this.blockSize = 16384;
	}

	public String getClusterName() {
		return clusterName;
	}

	public String getKeyspace() {
		return keyspace;
	}

	public String getColumnFamily() {
		return columnFamily;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public void setKeyspace(String keyspace) {
		this.keyspace = keyspace;
	}

	public void setColumnFamily(String columnFamily) {
		this.columnFamily = columnFamily;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public void addHost(String host) {
		this.hosts.add(host);
	}

	public String getHosts() {
		StringBuffer hostsString = new StringBuffer();
		for (String host : hosts) {
			hostsString.append(host);
			hostsString.append(",");
		}
		return hostsString.toString();
	}

	public void checkRequired() {
		if (this.clusterName == null || this.clusterName.equals(""))
			throw new IllegalArgumentException(
					"clusterName must not be null or empty");

		if (this.keyspace == null || this.keyspace.equals(""))
			throw new IllegalArgumentException(
					"keyspace must not be null or empty");

		if (this.columnFamily == null || this.columnFamily.equals(""))
			throw new IllegalArgumentException(
					"keyspace must not be null or empty");

		if (this.hosts == null || this.hosts.size() == 0)
			throw new IllegalArgumentException(
					"hosts must not be null and must be more than one host.");

		if (this.blockSize <= 0 || this.blockSize > Integer.MAX_VALUE)
			throw new IllegalArgumentException(
					"blockSize must not 0 or negative or greater than "
							+ Integer.MAX_VALUE);
	}

}