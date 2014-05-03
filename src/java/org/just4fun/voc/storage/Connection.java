package org.just4fun.voc.storage;

import java.io.IOException;

import org.just4fun.voc.file.BinaryFile;

public interface Connection {
	
	public boolean storeBinary(String name, BinaryFile file) throws IOException;
	
	public boolean deleteBinary(String name);
	
	public BinaryFile readBinary(String name) throws IOException;
	
}
