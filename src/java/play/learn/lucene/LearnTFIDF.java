package play.learn.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.DocsEnum;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermContext;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermStatistics;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Bits;

public class LearnTFIDF {

	public static void main(String[] args) throws Exception {
		// document frequency df
		
		Directory dir = FSDirectory.open(new File("clean/index.termrange"));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		
		Term term = new Term("9", "10");
		TermStatistics termStatistics = searcher.termStatistics(term, TermContext.build(reader.getContext(), term));
		System.out.println("\t total term frequency \t" + termStatistics.totalTermFreq());
		System.out.println("\t document frequency \t " + termStatistics.docFreq());
		
		// term frequency,
		Bits bits = MultiFields.getLiveDocs(reader);
		DocsEnum docsEnum = MultiFields.getTermDocsEnum(reader, bits, "foo", term.bytes());
		
		
		while (docsEnum != null && docsEnum.nextDoc() != DocsEnum.NO_MORE_DOCS) {
			final int freq = docsEnum.freq();
			int docID = docsEnum.docID();
			System.out.println(docID + " has frequency " + freq);
		}
		
		
		

	}

}
