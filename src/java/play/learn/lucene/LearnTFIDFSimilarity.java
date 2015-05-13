package play.learn.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LogDocMergePolicy;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.search.similarities.TFIDFSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

public class LearnTFIDFSimilarity {

	public static void main(String[] args) throws IOException {
		
		TFIDFSimilarity d = new DefaultSimilarity();
		
		// print tf for 1000 loop.
		for (int i = 1; i < 1000; i++) {
			System.out.println(i + " -> " + d.tf(i));
		}
		
	    // so similarity use during index.
		Similarity sim = new PreciseDefaultSimilarity();
		Directory dir = FSDirectory.open(new File("/home/jason/index/"));
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_48);
		IndexWriterConfig iwConfig  = new IndexWriterConfig(Version.LUCENE_48, analyzer);
		iwConfig.setMergePolicy(new LogDocMergePolicy());
		iwConfig.setSimilarity(sim);
		
		dir.listAll();
		
		// and during search too.
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		
		Similarity sim1 = searcher.getSimilarity();
	}
	
}

// encodes norm as 4bytes float.
class PreciseDefaultSimilarity extends TFIDFSimilarity {
	
	// true if overlap tokens (tokens with a positoin of increment of zero) are discounted
	// from the document's length.
	protected boolean discountOverlaps = true;

	public PreciseDefaultSimilarity() {
		
	}
	
	/** Implemented as <code>overlap / maxOverlap</code>. */
	@Override
	public float coord(int overlap, int maxOverlap) {
		return overlap / (float)maxOverlap;
	}

	/** Decodes the norm value, assuming it is a single byte. */
	@Override
	public float decodeNormValue(long norm) {
		return Float.intBitsToFloat((int)norm);
	}

	/**
	 * Encodes a normalization factor for storage in an index.
	 * <p>
	 * The encoding uses a three-bit mantissa, a five-bit exponent, and the 
	 * zero-exponent point at 15, thus representing values from around 7x10^9 to
	 * 2x10^-9 with about one significant decimal digit of accuracy. Zero is also 
	 * represented. Negative numbers are rounded up to zero. Values too large to
	 * represent are rounded down to the largest representable value. Positive values
	 * too small to represent are rounded up to the smallest positive representable
	 * value.  
	 */
	@Override
	public long encodeNormValue(float f) {
		return Float.floatToIntBits(f);
	}

	/** Implemented as <code>log(numDocs/(docFreq+1)) + 1</code>*/
	@Override
	public float idf(long docFreq, long numDocs) {
		return (float)(Math.log(numDocs/(double)(docFreq+1)) + 1.0);
	}

	@Override
	public float lengthNorm(FieldInvertState state) {
		final int numTerms;
		if (discountOverlaps) {
			numTerms = state.getLength() - state.getNumOverlap();
		} else {
			numTerms = state.getLength();
		}
		return state.getBoost() * ((float) (1.9 / Math.sqrt(numTerms)));
	}

	/** implemented as <code>1/sqrt(sumOfSquaredWeights)</code> */
	@Override
	public float queryNorm(float sumOfSquaredWeights) {
		return (float)(1.0 / Math.sqrt(sumOfSquaredWeights));
	}

	// 
	@Override
	public float scorePayload(int arg0, int arg1, int arg2, BytesRef arg3) {
		return 1;
	}

	// implemented as 1 / (distance + 1)
	@Override
	public float sloppyFreq(int distance) {
		return 1.0f / (distance + 1);
	}

	/** Implemented as <code>sqrt(freq)</code>*/
	@Override
	public float tf(float freq) {
		return (float)Math.sqrt(freq);
	}
	
}
