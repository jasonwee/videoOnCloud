package play.learn.java.concurrent;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LearnConcurrentInterfaceP1 {

	public static void main(String[] args) {
		
		/* blocking
		 * thread safe
		 * does not permit null elements
		 * may (or may not) be capacity-constrained. 
		 */
		BlockingDeque<Integer> bd = new LinkedBlockingDeque<Integer>(3);
		
		bd.add(1);
		bd.add(2);
		System.out.println("size: " + bd.size());
		
		bd.add(3);
		System.out.println("size: " + bd.size());
		//bd.add(4); // exception
		
		bd.forEach(s -> System.out.println(s));
		
		// --------------------
		
		/*
		 * blocking
		 * thread safe
		 * does not permit null elements
		 * may be capacity bounded.
		 * does not intrinsically support any kind of "close" or "shutdown" operation 
		 */
		BlockingQueue<Integer> bq = new ArrayBlockingQueue<Integer>(10);
		bq = new DelayQueue();
		bq = new LinkedBlockingDeque<Integer>();
		bq = new LinkedTransferQueue<Integer>();
		bq = new PriorityBlockingQueue<Integer>();
		bq = new SynchronousQueue<Integer>();
		
		// --------------------
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		
		// should really make into lambda since we are already using java8
		Future<Integer> future = executorService.submit(new Summer(11,22));
		
		try {
			Integer total = future.get();
			System.out.println("sum " + total);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		// --------------------
		
		// A marker interface identifying asynchronous tasks produced by async methods. 
		// This may be useful for monitoring, debugging, and tracking asynchronous activities.
		CompletableFuture<Integer> cf = new CompletableFuture<Integer>();
		ForkJoinPool.commonPool().submit(
				(Runnable & CompletableFuture.AsynchronousCompletionTask)()->{
			  try {
				  cf.complete(1);
			  } catch (Exception e) {
				  cf.completeExceptionally(e);
			  }
		  });
		
		// --------------------
		// CompletionService
		//  A service that decouples the production of new asynchronous tasks from the 
		// consumption of the results of completed tasks. 
		
		CompletionService<Integer> longRunningCompletionService = new ExecutorCompletionService<Integer>(executorService);
		
		longRunningCompletionService.submit(() -> {System.out.println("done"); return 1;});
		
		try {
			Future<Integer> result = longRunningCompletionService.take();
			System.out.println(result.get());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// --------------------
		
		// A Map providing thread safety and atomicity guarantees. 
		ConcurrentMap<String, String> cm = new ConcurrentHashMap();
		cm = new ConcurrentSkipListMap<String, String>();
		
		// --------------------
		// A ConcurrentMap supporting NavigableMap operations, and recursively so for its navigable sub-maps. 
		ConcurrentNavigableMap<String, String> cnm = new ConcurrentSkipListMap<String, String>();
		
		// --------------------
		// A mix-in style interface for marking objects that should be acted upon after a given delay. 
		Random random = new Random();
		int delay =  random.nextInt(10000);
		Delayed employer = new SalaryDelay("a lot of bs reasons", delay);
		System.out.println("bullshit delay this time " + employer.getDelay(TimeUnit.SECONDS));
		
		// --------------------
		// An object that executes submitted Runnable tasks. 
		// Executor interface does not strictly require that execution be asynchronous
		Executor executor = new ForkJoinPool();
		executor = new ScheduledThreadPoolExecutor(1);
		
		BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(4);
		executor = new ThreadPoolExecutor(1, 1, 1000, TimeUnit.SECONDS, blockingQueue);
	}

}
