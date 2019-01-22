package play.learn.java.design.throttling;

import java.util.Timer;
import java.util.TimerTask;

public class ThrottleTimerImpl implements Throttler {

	private final int throttlePeriod;
	private final CallsCount callsCount;

	public ThrottleTimerImpl(int throttlePeriod, CallsCount callsCount) {
		this.throttlePeriod = throttlePeriod;
		this.callsCount = callsCount;
	}

	/**
	 * A timer is initiated with this method. The timer runs every second and resets
	 * the counter.
	 */
	public void start() {
		new Timer(true).schedule(new TimerTask() {
			@Override
			public void run() {
				callsCount.reset();
			}
		}, 0, throttlePeriod);
	}

}
