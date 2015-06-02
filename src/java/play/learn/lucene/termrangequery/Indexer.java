package play.learn.lucene.termrangequery;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.testng.reporters.Files;

public class Indexer {
	
	private IndexWriter writer;
	
	public Indexer(String indexDirectoryPath) throws IOException {
		Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath));
		
		// create the indexer
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_48, new StandardAnalyzer(Version.LUCENE_48));
		writer = new IndexWriter(indexDirectory, iwc);
	}
	
	public void close() throws IOException {
		writer.close();
	}
	
	private Document getDocument(File file) throws IOException {
		Document document = new Document();
		
		// index file contents
		// Files.readFile  <- java8 !!
		Field contentField = new StringField(LuceneConstants.CONTENTS, Files.readFile(file), Field.Store.YES);
		// index file name
		Field fileNameField = new StringField(LuceneConstants.FILE_NAME, file.getName(), Field.Store.YES);
		// index file path
		Field filePathField = new StringField(LuceneConstants.FILE_PATH, file.getCanonicalPath(), Field.Store.YES);
		
		document.add(contentField);
		document.add(fileNameField);
		document.add(filePathField);
		
		return document;
	}
	
	private void indexFile(File file) throws IOException {
		System.out.println("Indexing " + file.getCanonicalPath());
		Document document = getDocument(file);
		writer.addDocument(document);
	}
	
	public int createIndex(String dataDirPath, FileFilter filter) throws IOException {
		// get all files in the data directory
		File[] files = new File(dataDirPath).listFiles();

		for (File file : files) {
			System.out.println(file.getName());
			if (!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead() && filter.accept(file)) {
				indexFile(file);
			}
		}
		
		return writer.numDocs();
	}
	
	

}
