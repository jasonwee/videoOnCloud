package play.learn.java.ForkJoinTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * http://www.concretepage.com/java/jdk7/example-of-forkjointask-in-java
 *
 */
public class LearnForkJoinTask {

	public static void main(String... args) {
		ForkJoinPool fjp = new ForkJoinPool();
		DemoTask task = new DemoTask();
		System.out.println(fjp.getPoolSize());
		ForkJoinTask<String> fjt = ForkJoinTask.adapt(task);
		System.out.println(fjp.getActiveThreadCount());
		System.out.println(fjp.invoke(fjt));
		System.out.println(fjp.getStealCount());
		System.out.println(ForkJoinPool.getCommonPoolParallelism());
		System.out.println(fjt.isDone());
	}
	
	static class DemoTask implements Callable<String> {
		
		public DemoTask() {
			
		}
		
		public String call() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
			return "Task Done";
		}
	}

}
