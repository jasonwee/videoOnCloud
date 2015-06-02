package play.learn.lucene.termrangequery;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Searcher {
	
	IndexSearcher indexSearcher;
	DirectoryReader r;
	QueryParser queryParser;
	Query query;
	
	public Searcher(String indexDirectoryPath) throws IOException {
		Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath));
		r = DirectoryReader.open(indexDirectory);
		indexSearcher = new IndexSearcher(r);
		queryParser = new QueryParser(Version.LUCENE_48, LuceneConstants.CONTENTS, new StandardAnalyzer(Version.LUCENE_48));
	}
	
	public TopDocs search(String searchQuery) throws ParseException, IOException {
		query = queryParser.parse(searchQuery);
		return indexSearcher.search(query, LuceneConstants.MAX_SEARCH);
	}
	
	public TopDocs search(Query query) throws IOException, ParseException {
		return indexSearcher.search(query, LuceneConstants.MAX_SEARCH);
	}
	
	public Document getDocument(ScoreDoc scoreDoc) throws IOException {
		return indexSearcher.doc(scoreDoc.doc);
	}
	
	public void close() throws IOException {
		r.close();
	}

}
