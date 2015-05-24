package play.learn.java.CompletableFuture;

import java.util.concurrent.CompletableFuture;

import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * http://java.dzone.com/articles/implementing-java-8
 * 
 * 
 *
 */
public class LearnCompletionStage {
	
	private final AsyncListenableTaskExecutor executor = new TaskExecutorAdapter(Runnable::run);
	private final CompletionStageFactory factory = new CompletionStageFactory(Runnable::run);
	
	public void doTest() {
		ListenableFuture<String> springListenableFuture = createSpringListenableFuture();
		
		CompletableCompletionStage<Object> completionStage = factory.createCompletionStage();
		springListenableFuture.addCallback(new ListenableFutureCallback<String>() {
		    @Override
		    public void onSuccess(String result) {
		    	System.out.println("onSuccess called");
		        completionStage.complete(result);
		    }
		    @Override
		    public void onFailure(Throwable t) {
		    	System.out.println("onFailure called");
		        completionStage.completeExceptionally(t);
		    }
		});
		
		completionStage.thenAccept(System.out::println);
		
	}
	
	public void doTestLaunch() {
		CompletableFuture<LaunchResult> missilesLaunched = getAuthorizationCode().thenApply(this::launchMissiles);

		missilesLaunched.thenApply(this::generateDamageReport).thenAccept(this::updateMainScreen).exceptionally(this::playAlertSound);
		
		missilesLaunched.thenAccept(LavaLamp::turnOn);
	}

	public ListenableFuture<String> createSpringListenableFuture() {
		return executor.submitListenable(() -> "testValue");
	}
	
	private Void playAlertSound(Throwable throwable) {
		return null;
	}
	
	private String generateDamageReport(LaunchResult result) {
		return null;
	}
	
	private void updateMainScreen(String s) {
		
	}
	
	private LaunchResult launchMissiles(AuthorizationCode authorizationCode) {
		return null;
	}
	
	private CompletableFuture<AuthorizationCode> getAuthorizationCode() {
		return new CompletableFuture<>();
	}
	
	private class AuthorizationCode {}
	
	private class LaunchResult {} 
	
	private static class LavaLamp {
		public static void turnOn(LaunchResult aBoolean) {
			
		}
	}
	

	public static void main(String[] args) {
		LearnCompletionStage c = new LearnCompletionStage();
		c.doTest();

	}

}
