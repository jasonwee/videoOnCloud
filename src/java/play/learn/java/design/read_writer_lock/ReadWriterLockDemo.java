package play.learn.java.design.read_writer_lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

// https://java-design-patterns.com/patterns/reader-writer-lock/
public class ReadWriterLockDemo {
	public static void main(String[] args) {

		ExecutorService executeService = Executors.newFixedThreadPool(10);
		ReaderWriterLock lock = new ReaderWriterLock();

		// Start writers
		IntStream.range(0, 5).forEach(i -> executeService.submit(new Writer("Writer " + i, lock.writeLock(), ThreadLocalRandom.current().nextLong(5000))));
		System.out.println("Writers added...");

		// Start readers
		IntStream.range(0, 5).forEach(i -> executeService.submit(new Reader("Reader " + i, lock.readLock(), ThreadLocalRandom.current().nextLong(10))));
		System.out.println("Readers added...");

		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			System.err.println("Error sleeping before adding more readers" + e);
			Thread.currentThread().interrupt();
		}

		// Start readers
		IntStream.range(6, 10).forEach(i -> executeService.submit(new Reader("Reader " + i, lock.readLock(), ThreadLocalRandom.current().nextLong(10))));
		System.out.println("More readers added...");

		// In the system console, it can see that the read operations are executed
		// concurrently while
		// write operations are exclusive.
		executeService.shutdown();
		try {
			executeService.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.err.println("Error waiting for ExecutorService shutdown" + e);
			Thread.currentThread().interrupt();
		}

	}
}
