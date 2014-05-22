package org.org.just4fun.voc.file;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.just4fun.voc.file.BinaryFile;

public class TestBinaryFile {
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	
	File testFile; 

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// should place in @BeforeClass but don't work there, not sure why. 
		testFile = folder.newFile("testFile.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
		writer.write("hello world");
		writer.close();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBinaryFile() {
		BinaryFile bf = new BinaryFile();
		assertNull(bf.getName());
		assertNull(bf.getDirectory());
		assertNull(bf.getHexContent());
		assertEquals(-1, bf.getLength());
		assertEquals(0, bf.getLastModified());
		assertEquals(0, bf.getLastAccessed());
		
		bf = new BinaryFile("/testDirectory", "fish.mp4", 1256, 1399393788131l, 1399393788131l);
		bf.setHexContent("68656c6c6f20776f726c640A");
		
		assertEquals("fish.mp4", bf.getName());
		assertEquals("/testDirectory", bf.getDirectory());
		assertEquals("68656c6c6f20776f726c640A", bf.getHexContent());
		assertEquals(1256, bf.getLength());
		assertEquals(1399393788131l, bf.getLastModified());
		assertEquals(1399393788131l, bf.getLastAccessed());
	}

	@Test
	public void testToHexFile() {
		try {
			String hex = BinaryFile.toHex(testFile);
			assertEquals("68656C6C6F20776F726C64", hex);
			
			hex = BinaryFile.toHex("hello world".getBytes());
			assertEquals("68656C6C6F20776F726C64", hex);
			
			// test large file.
			hex = BinaryFile.toHex(new File("test/resources/song.mp3"));
			String parsedStr = hex.replaceAll("(.{16384})", "$1\n");
			String[] list = parsedStr.split("\n");
			
			int i = 0;
			
			StringBuilder sb = new StringBuilder();
			try (BufferedReader br = new BufferedReader(new FileReader("test/resources/song.hex"))) {
				
				String line = br.readLine();
				
				while (line != null) {
					sb.append(line);
					assertEquals(line, list[i]);
					line = br.readLine();
					i++;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception is not expected");
		}
	}

	@Test
	public void testToByteArray() {
		try {
			byte[] b = BinaryFile.toByteArray("68656C6C6F20776F726C64");
			String check = new String(b);
			assertEquals("hello world", check);
		} catch (IOException e) {
			e.printStackTrace();
			fail("exception is not expected");
		}
	}

	@Test
	public void testToFile() {
		try {
			String directory = testFile.getParent();
			String removeMe = directory + "/dummy.txt";
			BinaryFile.toFile("68656c6c6f20776f726c64", removeMe);
			
			StringBuilder sb = new StringBuilder();
			try (BufferedReader br = new BufferedReader(new FileReader(removeMe))) {
				
				String line = br.readLine();
				
				while (line != null) {
					sb.append(line);
					line = br.readLine();
				}
			}
			
			assertEquals("hello world", sb.toString());
			
			// TEST LARGE FILE
			try (BufferedReader br = new BufferedReader(new FileReader("test/resources/song.hex"))) {
				
				String line = br.readLine();
				
				while (line != null) {
					sb.append(line);
					line = br.readLine();
				}
				
			}
			
			String binaryFile = sb.toString();
			assertEquals(8617321, binaryFile.length());

		} catch (IOException e) {
			e.printStackTrace();
			fail("exception is not expected");
		}

	}

	@Test
	public void testGetBinaryFile() {
		try {
			BinaryFile bf = BinaryFile.getBinaryFile("/testDirectory", "testFile.txt", testFile);
			
			 
		    assertEquals("/testDirectory", bf.getDirectory());
		    assertEquals("testFile.txt", bf.getName());
		    assertTrue(System.currentTimeMillis() >= bf.getLastAccessed());
		    assertTrue(System.currentTimeMillis() >= bf.getLastModified());
		    assertEquals("68656C6C6F20776F726C64", bf.getHexContent());
		} catch (IOException e) {
			e.printStackTrace();
			fail("exception is not expected");
		}
	}

}
