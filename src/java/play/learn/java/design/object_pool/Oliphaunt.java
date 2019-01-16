package play.learn.java.design.object_pool;

import java.util.concurrent.atomic.AtomicInteger;

public class Oliphaunt {

	private static AtomicInteger counter = new AtomicInteger(0);

	private final int id;

	/**
	 * Constructor
	 */
	public Oliphaunt() {
		id = counter.incrementAndGet();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return String.format("Oliphaunt id=%d", id);
	}

}
