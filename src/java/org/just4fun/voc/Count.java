package org.just4fun.voc;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

// https://github.com/PacktPublishing/Java-Coding-Problems/tree/master/Chapter06/P140_SearchInBigFiles
public class Count {
	
	// 5MB
	public static final long MAP_SIZE = 5242880;

	public static long countOccurerences(Path path, String text) throws IOException {
		final byte[] texttofind = text.getBytes(StandardCharsets.UTF_8);
		long count = 0;
		
		try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ)) {
			long position = 0;
			long length = fileChannel.size();
			
			while (position < length) {
				long remaining = length - position;
				long bytestomap = (long)Math.min(MAP_SIZE, remaining);
				
				MappedByteBuffer mbBuffer = fileChannel.map(MapMode.READ_ONLY, position, bytestomap);
				long limit = mbBuffer.limit();
				long lastSpace = -1;
				long firstChar = -1;
				
				while (mbBuffer.hasRemaining()) {
					boolean isFirstChar = false;
					while (firstChar != 0 && mbBuffer.hasRemaining()) {
						
						byte currentByte = mbBuffer.get();
						
						if (Character.isWhitespace((char) currentByte)) {
							lastSpace = mbBuffer.position();
						}
						
						if (texttofind[0] == currentByte) {
							isFirstChar = true;
							break;
						}
					}
					
					if (isFirstChar) {
						boolean isRestOfChars = true;
						
						int j;
						for (j = 1; j < texttofind.length; j++) {
							if (!mbBuffer.hasRemaining() || texttofind[j] != mbBuffer.get()) {
								isRestOfChars = false;
								break;
							}
						}
						
						if (isRestOfChars) {
							count++;
							lastSpace = -1;
						}
						firstChar = -1;
					}
				}
				
				if (lastSpace > -1) {
					position = position - (limit - lastSpace);
				}
				
				position += bytestomap;
			}
		}
		return count;
	}

}
