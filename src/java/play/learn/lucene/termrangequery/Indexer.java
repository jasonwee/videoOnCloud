package play.learn.lucene.termrangequery;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

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

		// index file name
		Field fileNameField = new StringField(LuceneConstants.FILE_NAME, file.getName(), Field.Store.YES);
		// index file path
		Field filePathField = new StringField(LuceneConstants.FILE_PATH, file.getCanonicalPath(), Field.Store.YES);
		//Field contentField = new TextField(LuceneConstants.CONTENTS, Files.newBufferedReader(file.toPath()), Store.YES);
		
		FieldType myFieldType = new FieldType();
		myFieldType.setIndexed(true);
		myFieldType.setOmitNorms(false);
		myFieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
		myFieldType.setStored(true);
		myFieldType.setTokenized(true);
		Field myField = new Field(LuceneConstants.CONTENTS, String.join("", Files.readAllLines(file.toPath())), myFieldType);
		
		
		document.add(filePathField);
		document.add(fileNameField);
		//document.add(contentField);
		document.add(myField);
		
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
