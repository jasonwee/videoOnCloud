package play.learn.java.CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class CompletionStageAdapter<T> implements CompletionStage<T> {
	
	protected final Executor defaultExecutor;
	
	public CompletionStageAdapter(Executor defaultExecutor) {
		this.defaultExecutor = defaultExecutor;
	}

	@Override
	public <U> CompletionStage<U> thenApply(Function<? super T, ? extends U> fn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> CompletionStage<U> thenApplyAsync(
			Function<? super T, ? extends U> fn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> CompletionStage<U> thenApplyAsync(
			Function<? super T, ? extends U> fn, Executor executor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletionStage<Void> thenAccept(Consumer<? super T> action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action,
			Executor executor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletionStage<Void> thenRun(Runnable action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletionStage<Void> thenRunAsync(Runnable action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletionStage<Void> thenRunAsync(Runnable action, Executor executor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U, V> CompletionStage<V> thenCombine(
			CompletionStage<? extends U> other,
			BiFunction<? super T, ? super U, ? extends V> fn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U, V> CompletionStage<V> thenCombineAsync(
			CompletionStage<? extends U> other,
			BiFunction<? super T, ? super U, ? extends V> fn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U, V> CompletionStage<V> thenCombineAsync(
			CompletionStage<? extends U> other,
			BiFunction<? super T, ? super U, ? extends V> fn, Executor executor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> CompletionStage<Void> thenAcceptBoth(
			CompletionStage<? extends U> other,
			BiConsumer<? super T, ? super U> action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> CompletionStage<Void> thenAcceptBothAsync(
			CompletionStage<? extends U> other,
			BiConsumer<? super T, ? super U> action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> CompletionStage<Void> thenAcceptBothAsync(
			CompletionStage<? extends U> other,
			BiConsumer<? super T, ? super U> action, Executor executor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletionStage<Void> runAfterBoth(CompletionStage<?> other,
			Runnable action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other,
			Runnable action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other,
			Runnable action, Executor executor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> CompletionStage<U> applyToEither(
			CompletionStage<? extends T> other, Function<? super T, U> fn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> CompletionStage<U> applyToEitherAsync(
			CompletionStage<? extends T> other, Function<? super T, U> fn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> CompletionStage<U> applyToEitherAsync(
			CompletionStage<? extends T> other, Function<? super T, U> fn,
			Executor executor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletionStage<Void> acceptEither(
			CompletionStage<? extends T> other, Consumer<? super T> action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletionStage<Void> acceptEitherAsync(
			CompletionStage<? extends T> other, Consumer<? super T> action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletionStage<Void> acceptEitherAsync(
			CompletionStage<? extends T> other, Consumer<? super T> action,
			Executor executor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletionStage<Void> runAfterEither(CompletionStage<?> other,
			Runnable action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other,
			Runnable action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other,
			Runnable action, Executor executor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> CompletionStage<U> thenCompose(
			Function<? super T, ? extends CompletionStage<U>> fn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> CompletionStage<U> thenComposeAsync(
			Function<? super T, ? extends CompletionStage<U>> fn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> CompletionStage<U> thenComposeAsync(
			Function<? super T, ? extends CompletionStage<U>> fn,
			Executor executor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletionStage<T> exceptionally(Function<Throwable, ? extends T> fn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletionStage<T> whenComplete(
			BiConsumer<? super T, ? super Throwable> action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletionStage<T> whenCompleteAsync(
			BiConsumer<? super T, ? super Throwable> action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletionStage<T> whenCompleteAsync(
			BiConsumer<? super T, ? super Throwable> action, Executor executor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> CompletionStage<U> handle(
			BiFunction<? super T, Throwable, ? extends U> fn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> CompletionStage<U> handleAsync(
			BiFunction<? super T, Throwable, ? extends U> fn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> CompletionStage<U> handleAsync(
			BiFunction<? super T, Throwable, ? extends U> fn, Executor executor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletableFuture<T> toCompletableFuture() {
		// TODO Auto-generated method stub
		return null;
	}

}
