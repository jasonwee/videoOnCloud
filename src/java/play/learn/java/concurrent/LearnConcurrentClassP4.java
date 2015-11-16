package play.learn.java.concurrent;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.Phaser;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LearnConcurrentClassP4 {

	public static void main(String[] args) throws Exception {
		
		// --------------------
		// ForkJoinTask
		ForkJoinTask<Integer> fjt = ForkJoinTask.adapt(new Summer(44,55));
		fjt.invoke();
		Integer sum = fjt.get();
		System.out.println(sum);
		System.out.println(fjt.isDone());
		fjt.join();
		
		// --------------------
		// ForkJoinWorkerThread
		ForkJoinWorkerThreadFactory customFactory = new ForkJoinWorkerThreadFactory() {

			@Override
			public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
				return null;
			}
			
		};
		
		
		// --------------------
		// FutureTask
		FutureTask<Integer> ft = new FutureTask<Integer>(new Summer(66,77));
		ft.run();
		System.out.println(ft.get());
		
		// --------------------
		LinkedBlockingDeque<Integer> lbd = new LinkedBlockingDeque<Integer>();
		lbd.add(1);
		lbd.add(2);
		lbd.add(3);

		// --------------------
		LinkedBlockingQueue<Integer> lbq = new LinkedBlockingQueue<Integer>();
		lbq.add(4);
		lbq.add(5);
		lbq.add(6);
		
		// --------------------
		LinkedTransferQueue<Integer> ltq = new LinkedTransferQueue<Integer>();
		ltq.add(7);
		ltq.add(8);
		ltq.add(9);
		
		// --------------------
		// phaser
		Phaser phaser = new Phaser();
		phaser.register();
		System.out.println("current phase number : " + phaser.getPhase());
		testPhaser(phaser, 2000);
		testPhaser(phaser, 4000);
		testPhaser(phaser, 6000);
		
		phaser.arriveAndDeregister();
		Thread.sleep(10000);
		System.out.println("current phase number : " + phaser.getPhase());

		// --------------------
		PriorityBlockingQueue<Integer> pbq = new PriorityBlockingQueue<Integer>();
		pbq.add(10);
		pbq.add(11);
		pbq.add(12);
		
		// --------------------
		//RecursiveAction
		long[] array = {1,2,3,4,5,6,7,8,9};
		RecursiveAction ar = new SortTask(array);
		
		// --------------------
		//RecursiveTask
		RecursiveTask<Integer> fibo = new Fibonacci(10);
		fibo.invoke();
		System.out.println(fibo.get());

		// --------------------
		ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(10);
		Future<Integer> total = stpe.submit(new Summer(88,99));
		System.out.println(total.get());
		stpe.shutdown();
		
		// --------------------
		//Semaphore
		// try put 2, blocking forever.
		ConnectionLimiter cl = new ConnectionLimiter(3);
		URLConnection conn = cl.acquire(new URL("http://www.google.com"));
		conn = cl.acquire(new URL("http://www.yahoo.com"));
		conn = cl.acquire(new URL("http://www.youtube.com"));
		cl.release(conn);
		
		// --------------------
		// SynchronousQueue
		final SynchronousQueue<String> queue = new SynchronousQueue<String>();
		Thread a = new Thread(new QueueProducer(queue));
		a.start();
		Thread b = new Thread(new QueueConsumer(queue));
		b.start();
		
		Thread.sleep(1000);
		
		a.interrupt();
		b.interrupt();
		
		// --------------------
		// ThreadLocalRandom
		ThreadLocalRandom tlr = ThreadLocalRandom.current();
		System.out.println(tlr.nextInt());
		
		// --------------------
		// ThreadPoolExecutor
		BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(4);
		ThreadPoolExecutor tpe = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, blockingQueue);
		
		// --------------------
		//ThreadPoolExecutor.AbortPolicy
		ThreadPoolExecutor.AbortPolicy ap = new ThreadPoolExecutor.AbortPolicy();
		try {
		ap.rejectedExecution(() -> System.out.println("abort"), tpe);
		} catch (Exception e) {
			
		}
		
		// --------------------
		//ThreadPoolExecutor.CallerRunsPolicy
		ThreadPoolExecutor.CallerRunsPolicy crp = new ThreadPoolExecutor.CallerRunsPolicy();
		try {
		crp.rejectedExecution(() -> System.out.println("run"), tpe);
		} catch (Exception e) {
			
		}
		
		// --------------------
		//ThreadPoolExecutor.DiscardOldestPolicy
		ThreadPoolExecutor.DiscardOldestPolicy dop = new ThreadPoolExecutor.DiscardOldestPolicy();
		try {
		dop.rejectedExecution(() -> System.out.println("abort"), tpe);
		} catch (Exception e) {
			
		}
		
		// --------------------
		//ThreadPoolExecutor.DiscardPolicy
		ThreadPoolExecutor.DiscardPolicy dp = new ThreadPoolExecutor.DiscardPolicy();
		try {
		dp.rejectedExecution(() -> System.out.println("discard"), tpe);
		} catch (Exception e) {
			
		}
		tpe.shutdown();
	}
	
	static void testPhaser(final Phaser phaser, final int sleepTime) {
		phaser.register();
		new Thread() {

			@Override
			public void run() {
				try {
					System.out.println(Thread.currentThread().getName() + "arrived");
					phaser.arriveAndAwaitAdvance(); // threads register arrival to the phaser.
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+" after passing barrier");
			}
		}.start();
		
	}
	
	static class SortTask extends RecursiveAction {
		
		final long[] array;
		final int lo, hi;
		
		SortTask(long[] array, int lo, int hi) {
			this.array = array;
			this.lo = lo;
			this.hi = hi;
		}
		
		SortTask(long[] array) {
			this(array, 0, array.length);
		}

		@Override
		protected void compute() {
			if (hi - lo < THRESHOLD)
				sortSequentially(lo,hi);
			else {
				int mid = (lo + hi) >>> 1;
				invokeAll(new SortTask(array, lo, mid), new SortTask(array, mid, hi));
				merge(lo, mid, hi);
			}
		}
		
		// implementation details follow:
		static final int THRESHOLD = 1000;
		
		void sortSequentially(int lo, int hi) {
			Arrays.sort(array, lo, hi);
		}
		
		void merge(int lo, int mid, int hi) {
			long[] buf = Arrays.copyOfRange(array, lo, mid);
			for (int i = 0, j = lo, k = mid; i < buf.length; j++)
				array[j] = (k == hi || buf[i] < array[k]) ? buf[i++] : array[k++];
		}
		 
	}
	
	static class Fibonacci extends RecursiveTask<Integer> {
		
		final int n;

		Fibonacci(int n) {
			this.n = n;
		}

		protected Integer compute() {
			if (n <= 1)
				return n;
			Fibonacci f1 = new Fibonacci(n - 1);
			f1.fork();
			Fibonacci f2 = new Fibonacci(n - 2);
			return f2.compute() + f1.join();
		}
	}
	
	static class ConnectionLimiter {
		private final Semaphore semaphore;
		
		private ConnectionLimiter(int max) {
			semaphore = new Semaphore(max);
		}
		
		public URLConnection acquire(URL url) throws IOException, InterruptedException {
			semaphore.acquire();
			return url.openConnection();
		}
		
		public void release(URLConnection conn) {
			try {
				// blahblah
			} finally {
				semaphore.release();
			}
		}
	}
	
	static class QueueProducer implements Runnable {
		
		private SynchronousQueue<String> queue;

		public QueueProducer(SynchronousQueue<String> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			String event = "SYNCHRONOUS_EVENT";
			String another_event = "ANOTHER_EVENT";
			
			try {
				queue.put(event);
				System.out.printf("[%s] published event : %s %n", Thread.currentThread().getName(), event);
				
				queue.put(another_event);
				System.out.printf("[%s] published event : %s %n", Thread.currentThread().getName(), another_event);
			} catch (InterruptedException e) {
			}
			
		}
		
	}
	
	static class QueueConsumer implements Runnable {
		
		private SynchronousQueue<String> queue;

		public QueueConsumer(SynchronousQueue<String> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			try {
				String event = queue.take();
				// thread will block here
				System.out.printf("[%s] consumed event : %s %n", Thread.currentThread().getName(), event);
			} catch (InterruptedException e) {
			}
			
		}
		
	}

}
