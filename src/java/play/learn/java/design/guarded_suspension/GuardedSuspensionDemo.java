package play.learn.java.design.guarded_suspension;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// https://java-design-patterns.com/patterns/guarded-suspension/
public class GuardedSuspensionDemo {
	
	  public static void main(String[] args) {
		    GuardedQueue guardedQueue = new GuardedQueue();
		    ExecutorService executorService = Executors.newFixedThreadPool(3);

		    //here we create first thread which is supposed to get from guardedQueue
		    executorService.execute(() -> {
		          guardedQueue.get();
		        }
		    );

		    //here we wait two seconds to show that the thread which is trying to get from guardedQueue will be waiting
		    try {
		      Thread.sleep(2000);
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    }
		    //now we execute second thread which will put number to guardedQueue and notify first thread that it could get
		    executorService.execute(() -> {
		          guardedQueue.put(20);
		        }
		    );
		    executorService.shutdown();
		    try {
		      executorService.awaitTermination(30, TimeUnit.SECONDS);
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    }
		  }

}
