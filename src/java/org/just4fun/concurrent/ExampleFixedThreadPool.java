package org.just4fun.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExampleFixedThreadPool {

	public static void main(String[] args) throws InterruptedException {
		
		ExampleFixedThreadPool me = new ExampleFixedThreadPool();
		
	     ExecutorService executor = Executors.newFixedThreadPool(20);
	        for (int i = 0; i < 100; i++) {
	            executor.submit(new NewStaticTask(i));
	            executor.submit(me.new NewTask(i));
	        }

	        executor.shutdown();
	        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

	}
	
	static class NewStaticTask implements Runnable {
		
		int i;
		
		public NewStaticTask(int i) {
			this.i = i;
		}

	    @Override
	    public void run() {
	    	System.out.println("measuring " + i);
	    }   
	}
	
	class NewTask implements Runnable {
		
		int i;
		
		public NewTask(int i) {
			this.i = i;
		}

	    @Override
	    public void run() {
	    	System.out.println("measuring " + i);
	    }   
	}

}



