package play.learn.java.countdownlatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

// http://www.baeldung.com/java-countdown-latch
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	@org.junit.Test
	public void whenParallelProcessing_thenMainThreadWillBlockUntilCompletion()
	  throws InterruptedException {
	 
	    List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
	    CountDownLatch countDownLatch = new CountDownLatch(5);
	    List<Thread> workers = Stream
	      .generate(() -> new Thread(new Worker(outputScraper, countDownLatch)))
	      .limit(5)
	      .collect(toList());
	 
	      workers.forEach(Thread::start);
	      countDownLatch.await(); 
	      outputScraper.add("Latch released");
	 
	      System.out.println(outputScraper.toString());
	      /*
	      assertThat(outputScraper)
	        .containsExactly(
	          "Counted down",
	          "Counted down",
	          "Counted down",
	          "Counted down",
	          "Counted down",
	          "Latch released"
	        );
	      */
	    }
	
	@org.junit.Test
	public void whenDoingLotsOfThreadsInParallel_thenStartThemAtTheSameTime()
	 throws InterruptedException {
	  
	    List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
	    CountDownLatch readyThreadCounter = new CountDownLatch(5);
	    CountDownLatch callingThreadBlocker = new CountDownLatch(1);
	    CountDownLatch completedThreadCounter = new CountDownLatch(5);
	    List<Thread> workers = Stream
	      .generate(() -> new Thread(new WaitingWorker(
	        outputScraper, readyThreadCounter, callingThreadBlocker, completedThreadCounter)))
	      .limit(5)
	      .collect(toList());
	 
	    workers.forEach(Thread::start);
	    readyThreadCounter.await(); 
	    outputScraper.add("Workers ready");
	    callingThreadBlocker.countDown();
	    // to await wait indefinitely (due to worker thread stuck)
	    completedThreadCounter.await(5L, TimeUnit.SECONDS); 
	    outputScraper.add("Workers complete");
	 
	   System.out.println(outputScraper.toString());
	}
}
