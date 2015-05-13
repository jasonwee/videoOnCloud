package play.learn.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FieldValueFilter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

// FieldValueFilter is removed in lucene 5.0
// https://lucene.apache.org/core/4_10_2/core/org/apache/lucene/search/FieldValueFilter.html
public class LearnFieldValueFilter {


	public static void main(String[] args) throws Exception {
		int docs = 10;
		Directory dir = FSDirectory.open(new File("/home/jason/index/"));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopDocs search = searcher.search(new TermQuery(new Term("all", "test")), new FieldValueFilter("some", true), docs);
		
		search = searcher.search(new TermQuery(new Term("all", "test")), new FieldValueFilter("some"), docs);
	}

}
