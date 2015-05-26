package org.just4fun.voc.compression;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

public class WriterReader {
	private static int BUFFERSIZE = 1024;	
	
	public static String read(String json_file_path) {
		StringBuilder sb = new StringBuilder();

		try (FileInputStream fin = new FileInputStream("src/resources/sample.json.bz2");
			   BufferedInputStream in = new BufferedInputStream(fin);
			   BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);)
		{
			final byte[] buffer = new byte[BUFFERSIZE];
			while (-1 != bzIn.read(buffer)) {
			    String text = new String(buffer);
			    sb.append(text);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static boolean write(String json, String json_file_path) {
		
		try (	InputStream is = new ByteArrayInputStream(json.getBytes());
				BufferedInputStream in = new BufferedInputStream(is);
				FileOutputStream out = new FileOutputStream(json_file_path);
				CompressorOutputStream bzip2Out = new CompressorStreamFactory().createCompressorOutputStream(CompressorStreamFactory.BZIP2, out);) {
			
			IOUtils.copy(is, bzip2Out);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public static void main(String[] args) {
		
		String gimmeJson = read("src/resources/sample.json.bz2");
		boolean doneWrite = write(gimmeJson, "clean/replicate.bz2");
		System.out.println("done write " + doneWrite);
	
	}

}
