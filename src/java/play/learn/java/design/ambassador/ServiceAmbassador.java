package play.learn.java.design.ambassador;

import static java.lang.Thread.sleep;


public class ServiceAmbassador implements RemoteServiceInterface {

	private static final int RETRIES = 3;
	private static final int DELAY_MS = 3000;

	ServiceAmbassador() {
	}

	@Override
	public long doRemoteFunction(int value) {
		return safeCall(value);
	}

	private long checkLatency(int value) {
		long startTime = System.currentTimeMillis();
		long result = RemoteService.getRemoteService().doRemoteFunction(value);
		long timeTaken = System.currentTimeMillis() - startTime;

		System.out.println("Time taken (ms): " + timeTaken);
		return result;
	}

	private long safeCall(int value) {

		int retries = 0;
		long result = FAILURE;

		for (int i = 0; i < RETRIES; i++) {

			if (retries >= RETRIES) {
				return FAILURE;
			}

			if ((result = checkLatency(value)) == FAILURE) {
				System.out.println("Failed to reach remote: (" + (i + 1) + ")");
				retries++;
				try {
					sleep(DELAY_MS);
				} catch (InterruptedException e) {
					System.out.println("Thread sleep state interrupted "+ e);
				}
			} else {
				break;
			}
		}
		return result;
	}

}
