package play.learn.java.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool.ManagedBlocker;

public class QueueManagedBlocker<T> implements ManagedBlocker {
	
	final BlockingQueue<T> queue;
	volatile T value = null;
	
	QueueManagedBlocker(BlockingQueue<T> queue) {
		this.queue = queue;
	}

	@Override
	public boolean block() throws InterruptedException {
		if (value == null)
			value = queue.take();
		return true;
	}

	@Override
	public boolean isReleasable() {
		return value != null || (value = queue.poll()) != null;
	}
	
	public T getValue() {
		return value;
	}

}
