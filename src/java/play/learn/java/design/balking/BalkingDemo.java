package play.learn.java.design.balking;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// https://java-design-patterns.com/patterns/balking/
public class BalkingDemo {

	public static void main(String[] args) {
		WashingMachine washingMachine = new WashingMachine();
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		
		for (int i = 0; i < 3; i++) {
			executorService.execute(washingMachine::wash);
		}
		executorService.shutdown();
		try {
			executorService.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException ie) {
			System.err.println("error : waiting on executor service shutdown!");
		}
	}

}
