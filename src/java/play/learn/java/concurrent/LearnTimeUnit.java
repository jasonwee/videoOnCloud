package play.learn.java.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeUnit;

public class LearnTimeUnit {
	
	public LearnTimeUnit() throws InterruptedException {
		
		// assuming we have a long running apps which ran for 2 days 7hours 35minutes 6 seconds 
		long longRunningApplicationDuration =  200102l;
		
		System.out.println("duration in nanos " + TimeUnit.SECONDS.toNanos(longRunningApplicationDuration));
		System.out.println("duration in days " + TimeUnit.SECONDS.toDays(longRunningApplicationDuration));
		System.out.println("duration in hours " + TimeUnit.SECONDS.toHours(longRunningApplicationDuration));
		System.out.println("duration in micros " + TimeUnit.SECONDS.toMicros(longRunningApplicationDuration));
		System.out.println("duration in millis " + TimeUnit.SECONDS.toMillis(longRunningApplicationDuration));
		System.out.println("duration in minutes " + TimeUnit.SECONDS.toMinutes(longRunningApplicationDuration));
		System.out.println("duration in seconds " + TimeUnit.SECONDS.toSeconds(longRunningApplicationDuration));
		
		
		TimeUnit[] var = TimeUnit.values();
		System.out.println("size " + var.length);
		
		for (TimeUnit elem : var) {
			System.out.println(elem.name());
		}
		
		TimeUnit.SECONDS.sleep(10);
	}

	public static void main(String[] args) throws InterruptedException {
		new LearnTimeUnit();
	}

}
