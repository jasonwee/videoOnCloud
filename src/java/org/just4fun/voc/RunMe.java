package org.just4fun.voc;


import java.io.File;
import java.io.IOException;

import org.just4fun.voc.file.BinaryFile;
import org.just4fun.voc.storage.Connection;
import org.just4fun.voc.storage.cassandra.CassandraClientConfiguration;
import org.just4fun.voc.storage.cassandra.ConnectionHector;




public class RunMe {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// convert a binary file to hex.
		//String hex = BinaryFile.toHex(new File("test.mp4"));
		
		// make binary file into cassandra file
		BinaryFile video = BinaryFile.getBinaryFile("", "fish.mp4", new File("test/resources/test.mp4"));
		
		
		// save binary file into cloud.
		CassandraClientConfiguration config = new CassandraClientConfiguration();
		Connection conn = new ConnectionHector(config);
		conn.storeBinary("fish.mp4", video);
		BinaryFile bf = conn.readBinary("fish.mp4");
		BinaryFile.toFile(bf.getHexContent(), "/home/jason/Desktop/fish.mp4");
		conn.deleteBinary("fish.mp4");
		
	}

}
