package play.learn.java.CompletableFuture;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public final class CallbackRegistry<T> {
	
	private State<T> state = NewEmptyState.instance();
	
	
	private static abstract class State<S> {
		protected abstract State<S> addCallbacks(Consumer<? super S> successCallback, Consumer<Throwable> failureCallback, Executor executor);
		
		protected State<S> success(S result) {
			throw new IllegalStateException("success method should not be called multiple times");
		}
		
		protected State<S> failure(Throwable failure) {
			throw new IllegalStateException("failure method should not be called multiple times");
		}
		
		protected boolean isCompleted() {
			return true;
		}
		
		protected static <S> void callCallback(Consumer<S> callback, S value, Executor executor) {
			executor.execute(() -> callback.accept(value));
		}
		
		protected static <S> void callCallback(CallbackExecutorPair<S> callbackExecutorPair, S result) {
			callCallback(callbackExecutorPair.getCallback(), result, callbackExecutorPair.getExecutor());
		}
		
		
	}
	
	/**
	 * Result is not known yet and no callbacks registered. Using shared instance so we do not allocate instance where
	 * it may not be needed.
	 * 
	 *
	 * @param <S>
	 */
	private static class NewEmptyState<S> extends State<S> {
		
		private static final NewEmptyState<Object> instance = new NewEmptyState<>();
		
		@Override
		protected State<S> addCallbacks(Consumer<? super S> successCallback, Consumer<Throwable> failureCallback, Executor executor) {
			NewState<S> newState = new NewState<>();
			newState.addCallbacks(successCallback, failureCallback, executor);
			return newState;
		}
		
		@SuppressWarnings("unchecked")
		private static <T> State<T> instance() {
			return (State<T>) instance;
		}
		
	}
	
	private static class NewState<S> extends State<S> {
		
		private final Queue<CallbackExecutorPair<? super S>> successCallbacks = new LinkedList<>();
		private final Queue<CallbackExecutorPair<Throwable>> failureCallbacks = new LinkedList<>();
		
		@Override
		protected State<S> addCallbacks(Consumer<? super S> successCallback, Consumer<Throwable> failureCallback, Executor executor) {
			successCallbacks.add(new CallbackExecutorPair<>(successCallback, executor));
			failureCallbacks.add(new CallbackExecutorPair<Throwable>(failureCallback, executor));
			return this;
		}
	}
	
	private static final class CallbackExecutorPair<S> {
		private final Consumer<S> callback;
		private final Executor executor;
		
		private CallbackExecutorPair(Consumer<S> callback, Executor executor) {
			this.callback = callback;
			this.executor = executor;
		}
		
		public Consumer<S> getCallback() {
			return callback;
		}
		
		public Executor getExecutor() {
			return executor;
		}
	}

}
