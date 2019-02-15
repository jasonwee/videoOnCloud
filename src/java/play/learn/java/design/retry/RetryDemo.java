package play.learn.java.design.retry;

// https://java-design-patterns.com/patterns/retry/
public class RetryDemo {
	private static BusinessOperation<String> op;

	/**
	 * Entry point.
	 * 
	 * @param args
	 *            not used
	 * @throws Exception
	 *             not expected
	 */
	public static void main(String[] args) throws Exception {
		noErrors();
		errorNoRetry();
		errorWithRetry();
		errorWithRetryExponentialBackoff();
	}

	private static void noErrors() throws Exception {
		op = new FindCustomer("123");
		op.perform();
		System.out.println("Sometimes the operation executes with no errors.");
	}

	private static void errorNoRetry() throws Exception {
		op = new FindCustomer("123", new CustomerNotFoundException("not found"));
		try {
			op.perform();
		} catch (CustomerNotFoundException e) {
			System.out.println("Yet the operation will throw an error every once in a while.");
		}
	}

	private static void errorWithRetry() throws Exception {
		final Retry<String> retry = new Retry<>(new FindCustomer("123", new CustomerNotFoundException("not found")), 3, // 3
																														// attempts
				100, // 100 ms delay between attempts
				e -> CustomerNotFoundException.class.isAssignableFrom(e.getClass()));
		op = retry;
		final String customerId = op.perform();
		System.out.println(String
				.format("However, retrying the operation while ignoring a recoverable error will eventually yield "
						+ "the result %s after a number of attempts %s", customerId, retry.attempts()));
	}

	private static void errorWithRetryExponentialBackoff() throws Exception {
		final RetryExponentialBackoff<String> retry = new RetryExponentialBackoff<>(
				new FindCustomer("123", new CustomerNotFoundException("not found")), 6, // 6 attempts
				30000, // 30 s max delay between attempts
				e -> CustomerNotFoundException.class.isAssignableFrom(e.getClass()));
		op = retry;
		final String customerId = op.perform();
		System.out.println(String
				.format("However, retrying the operation while ignoring a recoverable error will eventually yield "
						+ "the result %s after a number of attempts %s", customerId, retry.attempts()));
	}
}
