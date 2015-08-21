package org.just4fun.voc.storage;

import java.io.IOException;

import org.just4fun.voc.file.VirtualFile;

public interface VirtualFileDAO {
	
	public boolean storeBinary(VirtualFile virtualFile) throws IOException;
	
	public VirtualFile readVirtualFile(VirtualFile virtualFile) throws IOException;

	public boolean updateVirtualFile(VirtualFile virtualFile) throws IOException;
	
	public boolean deleteVirtualFile(VirtualFile virtualFile)  throws IOException;
	
}
