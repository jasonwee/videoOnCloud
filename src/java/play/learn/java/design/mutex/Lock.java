package play.learn.java.design.mutex;

public interface Lock {

	void acquire() throws InterruptedException;

	void release();
}
