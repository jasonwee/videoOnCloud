package play.learn.java.design.semaphore;

public interface Lock {
	void acquire() throws InterruptedException;

	void release();
}
