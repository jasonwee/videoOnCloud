package play.learn.java.design.throttling;

import java.util.concurrent.ThreadLocalRandom;

class B2BService {

	private final CallsCount callsCount;

	public B2BService(Throttler timer, CallsCount callsCount) {
		this.callsCount = callsCount;
		timer.start();
	}

	/**
	 *
	 * @return customer id which is randomly generated
	 */
	public int dummyCustomerApi(Tenant tenant) {
		String tenantName = tenant.getName();
		long count = callsCount.getCount(tenantName);
		System.out.printf("Counter for %s : %s ", tenant.getName(), count);
		if (count >= tenant.getAllowedCallsPerSecond()) {
			System.out.printf("API access per second limit reached for: %s", tenantName);
			return -1;
		}
		callsCount.incrementCount(tenantName);
		return getRandomCustomerId();
	}

	private int getRandomCustomerId() {
		return ThreadLocalRandom.current().nextInt(1, 10000);
	}

}
