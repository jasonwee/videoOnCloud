package play.learn.java.design.promise;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// https://java-design-patterns.com/patterns/promise/
public class PromiseDemo {
	private static final String DEFAULT_URL = "https://raw.githubusercontent.com/iluwatar/java-design-patterns/master/promise/README.md";
	private final ExecutorService executor;
	private final CountDownLatch stopLatch;

	private PromiseDemo() {
		executor = Executors.newFixedThreadPool(2);
		stopLatch = new CountDownLatch(2);
	}

	/**
	 * Program entry point
	 * 
	 * @param args
	 *            arguments
	 * @throws InterruptedException
	 *             if main thread is interrupted.
	 * @throws ExecutionException
	 *             if an execution error occurs.
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		PromiseDemo app = new PromiseDemo();
		try {
			app.promiseUsage();
		} finally {
			app.stop();
		}
	}

	private void promiseUsage() {
		calculateLineCount();

		calculateLowestFrequencyChar();
	}

	/*
	 * Calculate the lowest frequency character and when that promise is fulfilled,
	 * consume the result in a Consumer<Character>
	 */
	private void calculateLowestFrequencyChar() {
		lowestFrequencyChar().thenAccept(charFrequency -> {
			System.out.printf("Char with lowest frequency is: %s", charFrequency);
			taskCompleted();
		});
	}

	/*
	 * Calculate the line count and when that promise is fulfilled, consume the
	 * result in a Consumer<Integer>
	 */
	private void calculateLineCount() {
		countLines().thenAccept(count -> {
			System.out.printf("Line count is: %s", count);
			taskCompleted();
		});
	}

	/*
	 * Calculate the character frequency of a file and when that promise is
	 * fulfilled, then promise to apply function to calculate lowest character
	 * frequency.
	 */
	private Promise<Character> lowestFrequencyChar() {
		return characterFrequency().thenApply(Utility::lowestFrequencyChar);
	}

	/*
	 * Download the file at DEFAULT_URL and when that promise is fulfilled, then
	 * promise to apply function to calculate character frequency.
	 */
	private Promise<Map<Character, Integer>> characterFrequency() {
		return download(DEFAULT_URL).thenApply(Utility::characterFrequency);
	}

	/*
	 * Download the file at DEFAULT_URL and when that promise is fulfilled, then
	 * promise to apply function to count lines in that file.
	 */
	private Promise<Integer> countLines() {
		return download(DEFAULT_URL).thenApply(Utility::countLines);
	}

	/*
	 * Return a promise to provide the local absolute path of the file downloaded in
	 * background. This is an async method and does not wait until the file is
	 * downloaded.
	 */
	private Promise<String> download(String urlString) {
		return new Promise<String>().fulfillInAsync(() -> Utility.downloadFile(urlString), executor)
				.onError(throwable -> {
					throwable.printStackTrace();
					taskCompleted();
				});
	}

	private void stop() throws InterruptedException {
		stopLatch.await();
		executor.shutdownNow();
	}

	private void taskCompleted() {
		stopLatch.countDown();
	}

}
