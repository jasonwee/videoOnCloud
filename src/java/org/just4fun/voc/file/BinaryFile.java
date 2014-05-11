package org.just4fun.voc.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BinaryFile {
	
	private String name = null;
	private String directory = null;
	private String hexContent = null;
	
	private long length = -1;
	private long lastModified;
	private long lastAccessed;
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	public BinaryFile() {
		
	}
	
	public BinaryFile(String directory, String name, long length, long lastModified, long lastAccessed) {
		this.directory = directory;
		this.name = name;
		this.length = length;
		this.lastModified = lastModified;
		this.lastAccessed = lastAccessed;
	}
	
	public static String toHex(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		int value = 0;
		StringBuilder sbHex = new StringBuilder();

		while ((value = is.read()) != -1) {
			sbHex.append(String.format("%02X", value));
		}

		is.close();

		return sbHex.toString();
	}
	
	public static String toHex(byte[] bytes) throws IOException {
        char[] hexChars = new char[bytes.length * 2]; 
        for ( int j = 0; j < bytes.length; j++ ) { 
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4]; 
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);

	}
	
	public static byte[] toByteArray(String hex) throws IOException {
		int len = hex.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character
					.digit(hex.charAt(i + 1), 16));
		}
		return data;
	}
	
	public static void toFile(String hex, String file) throws IOException {
		int len = hex.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character
					.digit(hex.charAt(i + 1), 16));
		}

		OutputStream output = new BufferedOutputStream(new FileOutputStream(
				file));
		output.write(data);

		output.close();
	}
	
	public static BinaryFile getBinaryFile(String directory, String name, File file) throws IOException {
		
		String hex = toHex(file);
		
		BinaryFile binaryFile = new BinaryFile();
		binaryFile.setDirectory(directory);
		binaryFile.setName(name);
		binaryFile.setLastAccessed(System.currentTimeMillis());
		binaryFile.setLastModified(System.currentTimeMillis());
		binaryFile.setHexContent(hex);
		
		return binaryFile;
	}

	public String getName() {
		return name;
	}

	public String getDirectory() {
		return directory;
	}

	public long getLength() {
		return length;
	}

	public long getLastModified() {
		return lastModified;
	}

	public long getLastAccessed() {
		return lastAccessed;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public void setLastAccessed(long lastAccessed) {
		this.lastAccessed = lastAccessed;
	}

	public String getHexContent() {
		return hexContent;
	}

	public void setHexContent(String hexContent) {
		this.hexContent = hexContent;
	}

}
