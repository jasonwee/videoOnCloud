package org.just4fun.voc;


import java.io.File;
import java.io.IOException;

import org.just4fun.voc.file.BinaryFile;
import org.just4fun.voc.storage.Connection;
import org.just4fun.voc.storage.cassandra.CassandraClientConfiguration;
import org.just4fun.voc.storage.cassandra.ConnectionDatastaxDriver;
import org.just4fun.voc.storage.cassandra.ConnectionHector;




public class RunMe {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// convert a file to binary hex in string format.
		String hex = BinaryFile.toHex(new File("test/resources/test.mp4"));
		String part = hex.substring(0, 10);
		System.out.println(part);
		
		// make a binary file from a file.
		BinaryFile video = BinaryFile.getBinaryFile("", "fish.mp4", new File("test/resources/test.mp4"));
		
		
		// save binary file into cloud.
		int useCassandraVersion = 1;
		CassandraClientConfiguration config = null;
		Connection conn = null;
		BinaryFile bf = null;
		
		switch (useCassandraVersion) {
		case 1:
		// cassandra 1.2 or below
		config = new CassandraClientConfiguration();
		conn = new ConnectionHector(config);
		conn.storeBinary("fish.mp4", video);
		bf = conn.readBinary("fish.mp4");
		BinaryFile.toFile(bf.getHexContent(), "/home/jason/Desktop/fish.mp4");
		conn.deleteBinary("fish.mp4");
		break;
		case 3:
		// cassandra 3.0
		config = new CassandraClientConfiguration();
		conn = new ConnectionDatastaxDriver(config);
		bf = BinaryFile.getBinaryFile("/", "django.mp3", new File("./src/resources/django.mp3"));
		conn.storeBinary("django.mp3", bf);
		break;
		}
		
		
	}

}
