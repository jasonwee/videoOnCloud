package play.learn.java.design.read_writer_lock;

import java.util.concurrent.locks.Lock;


public class Reader implements Runnable {
	
	 private Lock readLock;

	  private String name;
	  
	  private long readingTime;

	  /**
	   * Create new Reader
	   * 
	   * @param name - Name of the thread owning the reader
	   * @param readLock - Lock for this reader
	   * @param readingTime - amount of time (in milliseconds) for this reader to engage reading
	   */
	  public Reader(String name, Lock readLock, long readingTime) {
	    this.name = name;
	    this.readLock = readLock;
	    this.readingTime = readingTime;
	  }
	  
	  /**
	   * Create new Reader who reads for 250ms
	   * 
	   * @param name - Name of the thread owning the reader
	   * @param readLock - Lock for this reader
	   */
	  public Reader(String name, Lock readLock) {
	    this(name, readLock, 250L);
	  }

	  @Override
	  public void run() {
	    readLock.lock();
	    try {
	      read();
	    } catch (InterruptedException e) {
	      System.out.println("InterruptedException when reading" + e);
	      Thread.currentThread().interrupt();
	    } finally {
	      readLock.unlock();
	    }
	  }

	  /**
	   * Simulate the read operation
	   * 
	   */
	  public void read() throws InterruptedException {
	    System.out.printf("%s begin", name);
	    Thread.sleep(readingTime);
	    System.out.printf("%s finish after reading %sms", name, readingTime);
	  }

}
