package play.learn.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.SlowCompositeReaderWrapper;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

	public class LearnTermDictionary {
	
		public static void main(String[] args) throws IOException {

			IndexReader indexReader = DirectoryReader.open(FSDirectory.open(new File("clean/index.termrange")));

			// all fields
			SlowCompositeReaderWrapper.wrap(indexReader).getFieldInfos().forEach(x -> System.out.println(x.name));
			
			Terms terms = SlowCompositeReaderWrapper.wrap(indexReader).terms("contents");
			
			TermsEnum iter = terms.iterator(null);
			
			BytesRef byteRef = null;
	        while((byteRef = iter.next()) != null) {
	            String term = new String(byteRef.bytes, byteRef.offset, byteRef.length);
	            	            
	            System.out.format("%-10s:%2d:%2d %n", term, indexReader.docFreq(new Term("contents", term)), indexReader.totalTermFreq(new Term("contents", term)));
	        }
	        
			System.out.println(terms.getSumTotalTermFreq());
			System.out.println(terms.getSumDocFreq());
			System.out.println(indexReader.getSumTotalTermFreq("contents"));
		}
	
	}
