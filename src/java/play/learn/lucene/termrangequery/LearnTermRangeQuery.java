package play.learn.lucene.termrangequery;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;

public class LearnTermRangeQuery {
	
	String indexDir = "clean/index.termrange/";
	String dataDir = "src/resources/samples.termrange";
	Searcher searcher;
	Indexer indexer;
	
	public static void main(String[] args) {
		LearnTermRangeQuery tester;
		
		try {
			tester = new LearnTermRangeQuery();
			tester.createIndex();
			tester.searchUsingTermRangeQuery("record2.txt", "record6.txt");
		} catch (Exception e) {
			
		}
	}

	private void createIndex() throws IOException {
		indexer = new Indexer(indexDir);
		int numIndexed;
		long startTime = System.currentTimeMillis();
		
		numIndexed = indexer.createIndex(dataDir, x -> {System.out.println(x);return x.getName().toLowerCase().endsWith("txt");});
		long endTime = System.currentTimeMillis();
		indexer.close();
		System.out.println(numIndexed+" File indexed, time taken: " +(endTime-startTime)+" ms");
	}

	private void searchUsingTermRangeQuery(String searchQueryMin, String searchQueryMax) throws IOException, ParseException {
		
		searcher = new Searcher(indexDir);
		long startTime = System.currentTimeMillis();
		// create the term query object
		Query query = TermRangeQuery.newStringRange(LuceneConstants.FILE_NAME, searchQueryMin, searchQueryMax, true, false);
		
		// do the search
		TopDocs hits = searcher.search(query);
		long endTime = System.currentTimeMillis();
		
		System.out.println(hits.totalHits + " documents found. Time :" + (endTime - startTime) + "ms");
		
		for (ScoreDoc scoreDoc : hits.scoreDocs) {
			Document doc = searcher.getDocument(scoreDoc);
			System.out.println("File : " + doc.get(LuceneConstants.FILE_PATH));
		}
		
		searcher.close();
		
		
	}

}
