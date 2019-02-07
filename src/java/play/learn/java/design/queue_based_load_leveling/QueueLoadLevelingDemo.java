package play.learn.java.design.queue_based_load_leveling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// https://java-design-patterns.com/patterns/queue-load-leveling/
public class QueueLoadLevelingDemo {

	// Executor shut down time limit.
	private static final int SHUTDOWN_TIME = 15;

	public static void main(String[] args) {

		// An Executor that provides methods to manage termination and methods that can
		// produce a Future for tracking progress of one or more asynchronous tasks.
		ExecutorService executor = null;

		try {
			// Create a MessageQueue object.
			MessageQueue msgQueue = new MessageQueue();

			System.out.println("Submitting TaskGenerators and ServiceExecutor threads.");

			// Create three TaskGenerator threads. Each of them will submit different number
			// of jobs.
			Runnable taskRunnable1 = new TaskGenerator(msgQueue, 5);
			Runnable taskRunnable2 = new TaskGenerator(msgQueue, 1);
			Runnable taskRunnable3 = new TaskGenerator(msgQueue, 2);

			// Create e service which should process the submitted jobs.
			Runnable srvRunnable = new ServiceExecutor(msgQueue);

			// Create a ThreadPool of 2 threads and
			// submit all Runnable task for execution to executor..
			executor = Executors.newFixedThreadPool(2);
			executor.submit(taskRunnable1);
			executor.submit(taskRunnable2);
			executor.submit(taskRunnable3);

			// submitting serviceExecutor thread to the Executor service.
			executor.submit(srvRunnable);

			// Initiates an orderly shutdown.
			System.out.println("Intiating shutdown. Executor will shutdown only after all the Threads are completed.");
			executor.shutdown();

			// Wait for SHUTDOWN_TIME seconds for all the threads to complete
			// their tasks and then shut down the executor and then exit.
			if (!executor.awaitTermination(SHUTDOWN_TIME, TimeUnit.SECONDS)) {
				System.out.println("Executor was shut down and Exiting.");
				executor.shutdownNow();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
