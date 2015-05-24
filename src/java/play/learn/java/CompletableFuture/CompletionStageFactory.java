package play.learn.java.CompletableFuture;

import java.util.concurrent.Executor;

public class CompletionStageFactory {
	
	private final Executor defaultAsyncExecutor;
	
	public CompletionStageFactory(Executor defaultAsyncExecutor) {
		this.defaultAsyncExecutor = defaultAsyncExecutor;
	}
	                     
	public <T> CompletableCompletionStage<T> createCompletionStage() {
		return new SimpleCompletionStage<>(defaultAsyncExecutor);
	}

}
