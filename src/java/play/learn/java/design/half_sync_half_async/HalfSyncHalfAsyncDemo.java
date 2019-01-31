package play.learn.java.design.half_sync_half_async;

import java.util.concurrent.LinkedBlockingQueue;

// https://java-design-patterns.com/patterns/half-sync-half-async/
public class HalfSyncHalfAsyncDemo {
	public static void main(String[] args) {
		AsynchronousService service = new AsynchronousService(new LinkedBlockingQueue<>());
		/*
		 * A new task to calculate sum is received but as this is main thread, it should
		 * not block. So it passes it to the asynchronous task layer to compute and
		 * proceeds with handling other incoming requests. This is particularly useful
		 * when main thread is waiting on Socket to receive new incoming requests and
		 * does not wait for particular request to be completed before responding to new
		 * request.
		 */
		service.execute(new ArithmeticSumTask(1000));

		/*
		 * New task received, lets pass that to async layer for computation. So both
		 * requests will be executed in parallel.
		 */
		service.execute(new ArithmeticSumTask(500));
		service.execute(new ArithmeticSumTask(2000));
		service.execute(new ArithmeticSumTask(1));
	}

	/**
	 * 
	 * ArithmeticSumTask
	 *
	 */
	static class ArithmeticSumTask implements AsyncTask<Long> {
		private long n;

		public ArithmeticSumTask(long n) {
			this.n = n;
		}

		/*
		 * This is the long running task that is performed in background. In our example
		 * the long running task is calculating arithmetic sum with artificial delay.
		 */
		@Override
		public Long call() throws Exception {
			return ap(n);
		}

		/*
		 * This will be called in context of the main thread where some validations can
		 * be done regarding the inputs. Such as it must be greater than 0. It's a small
		 * computation which can be performed in main thread. If we did validated the
		 * input in background thread then we pay the cost of context switching which is
		 * much more than validating it in main thread.
		 */
		@Override
		public void onPreCall() {
			if (n < 0) {
				throw new IllegalArgumentException("n is less than 0");
			}
		}

		@Override
		public void onPostCall(Long result) {
			// Handle the result of computation
			System.out.println(result.toString());
		}

		@Override
		public void onError(Throwable throwable) {
			throw new IllegalStateException("Should not occur");
		}
	}

	private static long ap(long i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			System.out.println("Exception caught." + e);
		}
		return i * (i + 1) / 2;
	}

}
