package play.learn.java.concurrent;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class SalaryDelay implements Delayed {
	
	private String element;
	private long expiryTime;
	
	public SalaryDelay(String element, long delay) {
		this.element = element;
		this.expiryTime = System.currentTimeMillis() + delay;
	}


	@Override
	public int compareTo(Delayed o) {
		if (this.expiryTime < ((SalaryDelay) o).expiryTime) {
			return -1;
		}
		if (this.expiryTime > ((SalaryDelay) o).expiryTime) {
			return 1;
		}
		return 0;
	}

	@Override
	public long getDelay(TimeUnit timeUnit) {
		long diff = expiryTime - System.currentTimeMillis();
		return timeUnit.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	@Override
	public String toString() {
		return element + ": " + expiryTime;
	}

}
