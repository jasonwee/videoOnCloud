import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class LearnThreadPoolExecutor extends ThreadPoolExecutor {
	
	private boolean isPaused;
	private ReentrantLock pauseLock = new ReentrantLock();
	private Condition unpaused = pauseLock.newCondition();
	
	public LearnThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
			RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				threadFactory, handler);
	}
	
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		pauseLock.lock();
		
		try {
			while (isPaused) unpaused.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			pauseLock.unlock();
		}
	}
	
	public void pause() {
		pauseLock.lock();
		try {
			isPaused = true;
		} finally {
			pauseLock.unlock();
		}
	}
	
	public void resume() {
		pauseLock.lock();
		try {
			isPaused = false;
			unpaused.signalAll();
		} finally {
			pauseLock.unlock();
		}
	}



	public static void main(String[] args) {

	}

}
