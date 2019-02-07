package play.learn.java.design.promise;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;

// https://java-design-patterns.com/patterns/promise/
public class Promise<T> extends PromiseSupport<T> {
	private Runnable fulfillmentAction;
	private Consumer<? super Throwable> exceptionHandler;

	/**
	 * Creates a promise that will be fulfilled in future.
	 */
	public Promise() {
	}

	/**
	 * Fulfills the promise with the provided value.
	 * 
	 * @param value
	 *            the fulfilled value that can be accessed using {@link #get()}.
	 */
	@Override
	public void fulfill(T value) {
		super.fulfill(value);
		postFulfillment();
	}

	/**
	 * Fulfills the promise with exception due to error in execution.
	 * 
	 * @param exception
	 *            the exception will be wrapped in {@link ExecutionException} when
	 *            accessing the value using {@link #get()}.
	 */
	@Override
	public void fulfillExceptionally(Exception exception) {
		super.fulfillExceptionally(exception);
		handleException(exception);
		postFulfillment();
	}

	private void handleException(Exception exception) {
		if (exceptionHandler == null) {
			return;
		}
		exceptionHandler.accept(exception);
	}

	private void postFulfillment() {
		if (fulfillmentAction == null) {
			return;
		}
		fulfillmentAction.run();
	}

	/**
	 * Executes the task using the executor in other thread and fulfills the promise
	 * returned once the task completes either successfully or with an exception.
	 * 
	 * @param task
	 *            the task that will provide the value to fulfill the promise.
	 * @param executor
	 *            the executor in which the task should be run.
	 * @return a promise that represents the result of running the task provided.
	 */
	public Promise<T> fulfillInAsync(final Callable<T> task, Executor executor) {
		executor.execute(() -> {
			try {
				fulfill(task.call());
			} catch (Exception ex) {
				fulfillExceptionally(ex);
			}
		});
		return this;
	}

	/**
	 * Returns a new promise that, when this promise is fulfilled normally, is
	 * fulfilled with result of this promise as argument to the action provided.
	 * 
	 * @param action
	 *            action to be executed.
	 * @return a new promise.
	 */
	public Promise<Void> thenAccept(Consumer<? super T> action) {
		Promise<Void> dest = new Promise<>();
		fulfillmentAction = new ConsumeAction(this, dest, action);
		return dest;
	}

	/**
	 * Set the exception handler on this promise.
	 * 
	 * @param exceptionHandler
	 *            a consumer that will handle the exception occurred while
	 *            fulfilling the promise.
	 * @return this
	 */
	public Promise<T> onError(Consumer<? super Throwable> exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
		return this;
	}

	/**
	 * Returns a new promise that, when this promise is fulfilled normally, is
	 * fulfilled with result of this promise as argument to the function provided.
	 * 
	 * @param func
	 *            function to be executed.
	 * @return a new promise.
	 */
	public <V> Promise<V> thenApply(Function<? super T, V> func) {
		Promise<V> dest = new Promise<>();
		fulfillmentAction = new TransformAction<V>(this, dest, func);
		return dest;
	}

	/**
	 * Accesses the value from source promise and calls the consumer, then fulfills
	 * the destination promise.
	 */
	private class ConsumeAction implements Runnable {

		private final Promise<T> src;
		private final Promise<Void> dest;
		private final Consumer<? super T> action;

		private ConsumeAction(Promise<T> src, Promise<Void> dest, Consumer<? super T> action) {
			this.src = src;
			this.dest = dest;
			this.action = action;
		}

		@Override
		public void run() {
			try {
				action.accept(src.get());
				dest.fulfill(null);
			} catch (Throwable throwable) {
				dest.fulfillExceptionally((Exception) throwable.getCause());
			}
		}
	}

	/**
	 * Accesses the value from source promise, then fulfills the destination promise
	 * using the transformed value. The source value is transformed using the
	 * transformation function.
	 */
	private class TransformAction<V> implements Runnable {

		private final Promise<T> src;
		private final Promise<V> dest;
		private final Function<? super T, V> func;

		private TransformAction(Promise<T> src, Promise<V> dest, Function<? super T, V> func) {
			this.src = src;
			this.dest = dest;
			this.func = func;
		}

		@Override
		public void run() {
			try {
				dest.fulfill(func.apply(src.get()));
			} catch (Throwable throwable) {
				dest.fulfillExceptionally((Exception) throwable.getCause());
			}
		}
	}
}
