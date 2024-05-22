package play.learn.java.CompletableFuture;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompletableFutureExample {
	
	public CompletableFutureExample() throws InterruptedException, ExecutionException, TimeoutException {
		
		// supply , take no input  and produce output
		// consumer, take an input and produce no output.
		// function , take an input and producer output.
		CompletableFuture.supplyAsync(this::createId).thenApply(this::convert).thenApply(this::dup).thenAccept(this::store).get();
		
		
		//calculateAsyncWithCancellation().Future<String> future = calculateAsyncWithCancellation();
		//future.get(1, TimeUnit.SECONDS); // CancellationException
		
		CompletableFuture.supplyAsync(() -> "hello").thenRun(() ->System.out.println("this stage will run"));
		CompletableFuture.supplyAsync(() -> 1/0).thenRun(() ->System.out.println("this stage wont get print"));
		
		// whenComplete	, handle method to handle all these exception
		CompletableFuture.supplyAsync(() -> 1/0).exceptionally(ex -> 1).thenRun(() ->System.out.println("this break stage will get print"));

	}
	
	public Future<String> calculateAsyncWithCancellation() throws InterruptedException {
		CompletableFuture<String> completableFuture = new CompletableFuture<>();
		
		Executors.newCachedThreadPool().submit(() -> {
		   Thread.sleep(500);
		   completableFuture.cancel(false);
		   return null;
		});
		return completableFuture;
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
		new CompletableFutureExample();
	}
	
	public UUID createId() {
		return UUID.randomUUID();
	}
	
	public String convert(UUID input) {
		return input.toString();
	}
	
	public String dup(String message) {
		return message + message;
	}
	
	public void store(String message) {
		System.out.println("message = " + message);
	}

}
