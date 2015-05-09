package play.learn.java.stamplock;

public class Reader implements Runnable {
	
	private final Counter counter;
	
	public Reader(Counter counter) {
		this.counter = counter;
	}

	@Override
	public void run() {
		while (true) {
			if (Thread.interrupted()) {
				break;
			}
			long count = counter.getCounter();
			
			if (count > LearnStampLock.TARGET_NUMBER) {
				LearnStampLock.publish(System.currentTimeMillis());
				break;
			}
		}

	}

}
