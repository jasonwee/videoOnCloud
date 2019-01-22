package play.learn.java.design.throttling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// https://java-design-patterns.com/patterns/throttling/
public class ThrottlingDemo {

	public static void main(String[] args) {
		CallsCount callsCount = new CallsCount();
		Tenant adidas = new Tenant("Adidas", 5, callsCount);
		Tenant nike = new Tenant("Nike", 6, callsCount);

		ExecutorService executorService = Executors.newFixedThreadPool(2);

		executorService.execute(() -> makeServiceCalls(adidas, callsCount));
		executorService.execute(() -> makeServiceCalls(nike, callsCount));

		executorService.shutdown();
		try {
			executorService.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.out.printf("Executor Service terminated: %s", e.getMessage());
		}
	}

	/**
	 * Make calls to the B2BService dummy API
	 */
	private static void makeServiceCalls(Tenant tenant, CallsCount callsCount) {
		Throttler timer = new ThrottleTimerImpl(10, callsCount);
		B2BService service = new B2BService(timer, callsCount);
		for (int i = 0; i < 20; i++) {
			service.dummyCustomerApi(tenant);
			// Sleep is introduced to keep the output in check and easy to view and analyze
			// the results.
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				System.out.printf("Thread interrupted: %s", e.getMessage());
			}
		}
	}

}
