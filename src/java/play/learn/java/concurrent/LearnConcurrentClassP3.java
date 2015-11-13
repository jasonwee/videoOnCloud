package play.learn.java.concurrent;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LearnConcurrentClassP3 {

	public static void main(String[] args) throws Exception {
		
		AbstractExecutorService aes = null;
		
		aes = new ForkJoinPool();
		System.out.println(aes.isShutdown());
		Future<Integer> total = aes.submit(new Summer(33, 44));
		System.out.println(total.get());
		aes.shutdown();
		
		
		BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(4);
		aes = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, blockingQueue);
		System.out.println(aes.isShutdown());
		total = aes.submit(new Summer(33, 44));
		System.out.println(total.get());
		aes.shutdown();
		
		// --------------------
		
		ArrayBlockingQueue<Integer> abq = new ArrayBlockingQueue<Integer>(5);
		abq.add(1);
		abq.offer(2);
		System.out.println(abq.size());
		
		// --------------------
		CompletableFuture<Integer> cf = new CompletableFuture<Integer>();
		System.out.println(cf.isCancelled());
		// --------------------
		ConcurrentHashMap<String,Integer> chm = new ConcurrentHashMap<String,Integer>();
		chm.put("one", 1);
		chm.put("two", 2);
		chm.put("six", 6);
		
		// --------------------
		ConcurrentHashMap.KeySetView<String, Integer> keys = chm.keySet(10);
		System.out.println(keys.isEmpty());
		System.out.println(keys.toString());
		keys.add("ten");
		
		keys.forEach((s) -> System.out.println(s));
		System.out.println(chm.toString());
		
		// --------------------
		ConcurrentLinkedDeque<Integer> cldq = new ConcurrentLinkedDeque<Integer>(); 
		cldq.add(1);
		cldq.add(2);
		cldq.add(3);
		
		// --------------------
		ConcurrentLinkedQueue<Integer> clq = new  ConcurrentLinkedQueue<Integer>();
		clq.add(4);
		clq.add(5);
		clq.add(6);
		
		// --------------------
		ConcurrentSkipListMap<String,Integer> cslm = new ConcurrentSkipListMap<String, Integer>();
		cslm.put("one", 1);
		cslm.put("two", 2);
		cslm.put("six", 6);
		
		// --------------------
		ConcurrentSkipListSet<Integer> csls = new ConcurrentSkipListSet<Integer>();
		csls.add(1);
		csls.add(1);
		System.out.println("set size " + csls.size());
		
		// --------------------
		CopyOnWriteArrayList<Integer> cowal = new CopyOnWriteArrayList<Integer>();
		cowal.add(1);
		cowal.add(2);
		cowal.add(3);
		
		// --------------------
		CopyOnWriteArraySet<Integer> cowas = new CopyOnWriteArraySet<Integer>();
		cowas.add(1);
		cowas.add(1);
		System.out.println("set size " + cowas.size());
		
		// --------------------
		// CountDownLatch
		int N = 10;
		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch doneSignal = new CountDownLatch(N);
		
		for (int i = 0; i < N; ++i) // create and start threads
			new Thread(new Worker(startSignal, doneSignal)).start();

		doSomethingElse();            // don't let run yet
		startSignal.countDown();   // let all threads proceed
		doSomethingElse();
		doneSignal.await();            // wait for all to finish

		
		// --------------------
		// CountedCompleter<T>
		Integer[] numbers = {1,2,3,4,5};
		// null ?
		MapReducer<Integer> numbersReducer = new MapReducer<Integer>(null, numbers, new MyMapper(), new MyReducer(), 1, 10);
		Integer result = numbersReducer.getRawResult();
		System.out.println(result);

		// --------------------
		// CyclicBarrier
		float[][] matrix = {{1,2}, {2,3}};
		new Solver(matrix);
		
		// --------------------
		// DelayQueue<E extends Delayed>
		DelayQueue<SalaryDelay> delayQueue = new DelayQueue<SalaryDelay>();
		delayQueue.add(new SalaryDelay("August", 1));
		delayQueue.add(new SalaryDelay("September", 2));
		delayQueue.add(new SalaryDelay("October", 3));
		
		System.out.println(delayQueue.size());
		System.out.println(delayQueue.poll());
		
		// --------------------
		// Exchanger<V>
		Exchanger<?> exchanger = new Exchanger<>();
		ExchangerRunnable exchangerRunnable1 = new ExchangerRunnable(exchanger, "keychain");
		ExchangerRunnable exchangerRunnable2 = new ExchangerRunnable(exchanger, "chocalate");
		
		new Thread(exchangerRunnable1).start();
		new Thread(exchangerRunnable2).start();
		
		// --------------------
		// ExecutorCompletionService<V>
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		CompletionService<Integer> longRunningCompletionService = new ExecutorCompletionService<Integer>(executorService);
		longRunningCompletionService.submit(() -> {System.out.println("done"); return 1;});
		executorService.shutdown();
		
		// --------------------
		// Executors
		Executors.newCachedThreadPool();
		Executors.defaultThreadFactory();
		Executors.newFixedThreadPool(10);
		Executors.newScheduledThreadPool(1);
		Executors.newSingleThreadExecutor();
		Executors.privilegedThreadFactory();
		Executors.newWorkStealingPool();
		
		// --------------------
		// ForkJoinPool
		ForkJoinPool fjPool = new ForkJoinPool();
		Future<Integer> sum = fjPool.submit(new Summer(11, 89));
		System.out.println(sum.get());
		fjPool.shutdown();

	}
	
	
	private static void doSomethingElse() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " doing something else");
		Thread.sleep(1000);
	}


	static class Worker implements Runnable {
		   private final CountDownLatch startSignal;
		   private final CountDownLatch doneSignal;
		   Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
		     this.startSignal = startSignal;
		     this.doneSignal = doneSignal;
		   }
		   public void run() {
		     try {
		       startSignal.await();
		       doWork();
		       doneSignal.countDown();
		     } catch (InterruptedException ex) {} // return;
		   }

		   void doWork() { System.out.println(Thread.currentThread().getName() + " doing work"); }
		 }

}
