package play.learn.java.stamplock;

public class Writer implements Runnable {
	
	private final Counter counter;
	
	public Writer(Counter counter) {
		this.counter = counter;
	}

	@Override
	public void run() {
		while (true) {
			if (Thread.interrupted()) {
				break;
			}
			counter.increment();
		}
	}

}
