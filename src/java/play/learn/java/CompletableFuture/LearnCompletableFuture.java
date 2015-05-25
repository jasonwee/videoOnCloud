package play.learn.java.CompletableFuture;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.google.common.base.Throwables;

/**
 * java8
 * http://www.nurkiewicz.com/2013/05/java-8-completablefuture-in-action.html
 * http://www.nurkiewicz.com/2013/05/java-8-definitive-guide-to.html
 * 
 * a simpliefied version of completeablefuture adapted from the blog above. 
 * 
 *
 */
public class LearnCompletableFuture {
	
	private static Logger log = LoggerFactory.getLogger(LearnCompletableFuture.class);
	
	ExecutorService executor = Executors.newFixedThreadPool(4);
	
	List<String> topSites;
	
	public LearnCompletableFuture() {
		
	}
	
	// invoke by thread in the thread pool.
	private String downloadSite(final String site) {
		try {
			log.debug("Downloading {}", site);
			final String res = IOUtils.toString(new URL("http://" + site), StandardCharsets.UTF_8);
			log.debug("Done {}", site);
	        return res;
		} catch (IOException e) {
	        throw Throwables.propagate(e);
	    }
	}
	
	private Document parse(String xml) {
		return null;
	}
	
	private CompletableFuture<Double> calculateRelevance(Document doc) {

		return null;
	}
	
	private static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
	    CompletableFuture<Void> allDoneFuture =
	            CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
	        return allDoneFuture.thenApply(v ->
	                futures.stream().
	                        map(future -> future.join()).
	                        collect(Collectors.<T>toList())
	        );
	    
	    
	}
	
	public void crawlTheWeb() {
		topSites = Arrays.asList("www.google.com", "www.youtube.com", "www.yahoo.com", "www.msn.com");
		
		/* There is this chained expression computing relevanceFutures. 
		 * The sequence of map() and collect() in the end is quite descriptive. Starting 
		 * from a list of web sites we transform each site (String) into 
		 * CompletableFuture<String> by submitting asynchronous task 
		 * (downloadSite()) into thread pool.
		 * 
		 * So we have a list of CompletableFuture<String>. We continue transforming 
		 * it, this time applying parse() method on each of them. Remember that 
		 * thenApply() will invoke supplied lambda when underlying future completes 
		 * and returns CompletableFuture<Document> immediately. Third and last 
		 * transformation step composes each CompletableFuture<Document> in the 
		 * input list with calculateRelevance(). Note that calculateRelevance() returns
		 * CompletableFuture<Double> instead of Double, thus we use thenCompose() 
		 * rather than thenApply(). After that many stages we finally collect() a list of 
		 * CompletableFuture<Double>. 
		 *
		List<CompletableFuture<Double>> relevanceFutures = topSites.stream().
		        map(site -> CompletableFuture.supplyAsync(() -> downloadSite(site), executor))
		        .map(contentFuture -> contentFuture.thenApply(this::parse))
		        .map(docFuture -> docFuture.thenCompose(this::calculateRelevance))
		        .collect(Collectors.<CompletableFuture<Double>>toList());
		*/
		List<CompletableFuture<String>> relevanceFutures = topSites.stream()
				.map(site -> CompletableFuture.supplyAsync(() -> downloadSite(site), executor))
		        .collect(Collectors.<CompletableFuture<String>>toList());
		
		/* Now we would like to run some computations on all results. We have a list of 
		 * futures and we would like to know when all of them (last one) complete. Of 
		 * course we can register completion callback on each future and use 
		 * CountDownLatch to block until all callbacks are invoked. I am too lazy for 
		 * that, let us utilize existing CompletableFuture.allOf(). Unfortunately it has 
		 * two minor drawbacks - takes vararg instead of Collection and doesn't return 
		 * a future of aggregated results but Void instead. By aggregated results I 
		 * mean: if we provide List<CompletableFuture<Double>> such method 
		 * should return CompletableFuture<List<Double>>, not 
		 * CompletableFuture<Void>! Luckily it's easy to fix with a bit of glue code:
		 * 
		 */
		/*
		CompletableFuture<List<Double>> allDone = sequence(relevanceFutures);
	    CompletableFuture<OptionalDouble> maxRelevance = allDone.thenApply(relevances ->
	            relevances.stream().
	                    mapToDouble(Double::valueOf).
	                    max()
	    );
	    */
		CompletableFuture<List<String>> allDone = sequence(relevanceFutures);
		List<String> result = allDone.join();
		
		for (String r : result) {
			System.out.println(r);
		}
		executor.shutdown();
	}
	
	public void learnCompletionException() {
		try {
			List<String> list = Arrays.asList("A", "B", "C", "D");
			list.stream().map(s->CompletableFuture.supplyAsync(() -> s+s))
			.map(f->f.getNow("Not Done")).forEach(s->System.out.println(s));

		} catch (CompletionException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		LearnCompletableFuture c = new LearnCompletableFuture();
		//c.crawlTheWeb();
		c.learnCompletionException();
	}

}
