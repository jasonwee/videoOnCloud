package play.learn.java.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ForkJoinPool.ManagedBlocker;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TransferQueue;

public class LearnConcurrentInterfaceP2 {

	public static void main(String[] args) throws Exception {

		// no need to implement this for basic developer, just use class ForkJoinPool which
		// inner implementation of ForkJoinWorkerThreadFactory.
		// ForkJoinPool.ForkJoinWorkerThreadFactory
		
		// --------------------
		BlockingQueue<String> bq = new ArrayBlockingQueue<String>(2);
		bq.put("A");
		bq.put("B");
		QueueManagedBlocker<String> blocker = new QueueManagedBlocker<String>(bq);
		ForkJoinPool.managedBlock(blocker);
		System.out.println(blocker.isReleasable());
		System.out.println(blocker.getValue());

		// --------------------
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		Future<Integer> future = executorService.submit(new Summer(11,22));
		executorService.shutdown();
		
		
		// --------------------
		// RejectedExecutionHandler see 
		//src/java/org/just4fun/concurrent/MyRejectedExecutionHandlerImpl.java
		//src/java/org/just4fun/concurrent/DemoExecutor.java
		//src/java/org/just4fun/concurrent/ExampleThreadPoolExecutor.java
		
		// --------------------
		RunnableFuture<Integer> rf = new FutureTask<Integer>(new Summer(22,33));
		
		// --------------------
		RunnableScheduledFuture<Integer> rsf = new Summer1();
		System.out.println(rsf.isPeriodic());
		
		// --------------------
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(() -> System.out.println("hihi"), 1, 1, TimeUnit.SECONDS);
		Thread.sleep(3000);
		scheduler.shutdown();
		
		// --------------------
		ScheduledFuture<Integer> sf = new ScheduledFutureImpl();
		sf.isCancelled();
		
		// --------------------
		ThreadFactory tf = Executors.defaultThreadFactory();
		tf.newThread(()->System.out.println("ThreadFactory")).start();
		
		// --------------------
		TransferQueue<Integer> tq = new LinkedTransferQueue<Integer>();
		
	}

}
