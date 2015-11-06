package org.just4fun.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DemoExecutor {

	/**
	 * 
    If fewer than corePoolSize threads are running, the Executor always prefers adding a new thread rather than queuing.
    If corePoolSize or more threads are running, the Executor always prefers queuing a request rather than adding a new thread.
    If a request cannot be queued, a new thread is created unless this would exceed maximumPoolSize, in which case, the task will be rejected.
	 */
	
	public static void main(String[] args) {
		Integer threadCounter = 0;
		BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(50);
		
		CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor(10, 20, 5000, TimeUnit.MILLISECONDS, blockingQueue);
		
		executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				System.out.println("DemoTask Rejected : " + ((DemoThread ) r).getName());
				System.out.println("Waiting for a second !!");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Lets add another time : " + ((DemoThread) r).getName());
				executor.execute(r);
			}
		});
		// Let start all core threads initially
		executor.prestartAllCoreThreads();
		while (true) {
			threadCounter++;
			// Adding threads one by one
			System.out.println("Adding DemoTask : " + threadCounter);
			executor.execute(new DemoThread(threadCounter.toString()));
			
			if (threadCounter == 100)
				break;
		}
		
	}

}
