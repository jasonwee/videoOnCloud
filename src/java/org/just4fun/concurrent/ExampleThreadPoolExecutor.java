package org.just4fun.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExampleThreadPoolExecutor {
	
	private BlockingQueue<Runnable> worksQueue;
	private RejectedExecutionHandler executionHandler;
	private ThreadPoolExecutor executor;
	private Thread monitor;
	
	public ExampleThreadPoolExecutor() {
		worksQueue = new ArrayBlockingQueue<Runnable>(2);
		executionHandler = new MyRejectedExecutionHandlerImpl();
		
		// create the threadpoolExecutor
		executor = new ThreadPoolExecutor(3, 3, 10, TimeUnit.SECONDS, worksQueue, executionHandler);
		executor.allowCoreThreadTimeOut(true);
		
		monitor = new Thread(new MyMonitorThread(executor));
		monitor.setDaemon(true);
	}
	
	public boolean start() {
		monitor.start();
		
		// change this to listener.
		executor.execute(new MyWork("1"));
		executor.execute(new MyWork("2"));
		executor.execute(new MyWork("3"));
		executor.execute(new MyWork("4"));
		executor.execute(new MyWork("5"));
		executor.execute(new MyWork("6"));
		executor.execute(new MyWork("7"));
		executor.execute(new MyWork("8"));
		
		try 
		{
			Thread.sleep(30000);
			executor.execute(new MyWork("9"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}

	public static void main(String[] args) {
		ExampleThreadPoolExecutor server = new ExampleThreadPoolExecutor();
		server.start();
	}
	

}
