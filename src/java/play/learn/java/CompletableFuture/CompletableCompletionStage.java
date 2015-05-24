package play.learn.java.CompletableFuture;

import java.util.concurrent.CompletionStage;

public interface CompletableCompletionStage<T> extends CompletionStage<T> {
	
	public boolean complete(T result);
	
	public boolean completeExceptionally(Throwable ex);
	

}
