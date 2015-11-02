package play.learn.java.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.FutureTask;

public class LearnCancellationException {

	public static void main(String[] args) {
		MyCallable callable1 = new MyCallable(1000);
		MyCallable callable2 = new MyCallable(2000);

		FutureTask<String> futureTask1 = new FutureTask<String>(callable1);
		FutureTask<String> futureTask2 = new FutureTask<String>(callable2);

		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(futureTask1);
		executor.execute(futureTask2);

		while (true) {
			try {
				if(futureTask1.isDone() && futureTask2.isDone()){
					System.out.println("Done");
					//shut down executor service
					executor.shutdown();
					return;
				}
				
				// uncomment for cancel
				//futureTask2.cancel(true);

				if(!futureTask1.isDone()){
				//wait indefinitely for future task to complete
				System.out.println("FutureTask1 output="+futureTask1.get());
				}

				System.out.println("Waiting for FutureTask2 to complete");
				// set a samll range to get timedout exception.
				String s = futureTask2.get(2000L, TimeUnit.MILLISECONDS);
				if(s !=null){
					System.out.println("FutureTask2 output="+s);
				}
			} catch (CancellationException e) {
				e.printStackTrace();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			} catch(TimeoutException e){
				e.printStackTrace();
			}
		}

	}
	
	static class MyCallable implements Callable<String> {
		
		private long waitTime;
		
		public MyCallable(int timeInMillis) {
			this.waitTime = timeInMillis;
		}

		@Override
		public String call() throws Exception {
			Thread.sleep(waitTime);
			return Thread.currentThread().getName();
		}
		
	}

}
