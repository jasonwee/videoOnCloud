package play.learn.java.design.read_writer_lock;

import java.util.concurrent.locks.Lock;


public class Writer implements Runnable {

	private Lock writeLock;

	private String name;

	private long writingTime;

	/**
	 * Create new Writer who writes for 250ms
	 * 
	 * @param name
	 *            - Name of the thread owning the writer
	 * @param writeLock
	 *            - Lock for this writer
	 */
	public Writer(String name, Lock writeLock) {
		this(name, writeLock, 250L);
	}

	/**
	 * Create new Writer
	 * 
	 * @param name
	 *            - Name of the thread owning the writer
	 * @param writeLock
	 *            - Lock for this writer
	 * @param writingTime
	 *            - amount of time (in milliseconds) for this reader to engage
	 *            writing
	 */
	public Writer(String name, Lock writeLock, long writingTime) {
		this.name = name;
		this.writeLock = writeLock;
		this.writingTime = writingTime;
	}

	@Override
	public void run() {
		writeLock.lock();
		try {
			write();
		} catch (InterruptedException e) {
			System.out.println("InterruptedException when writing" + e);
			Thread.currentThread().interrupt();
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Simulate the write operation
	 */
	public void write() throws InterruptedException {
		System.out.printf("%s begin", name);
		Thread.sleep(writingTime);
		System.out.printf("%s finished after writing %sms", name, writingTime);
	}

}
