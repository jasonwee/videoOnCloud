package play.learn.java.design.double_checked_locking;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// https://java-design-patterns.com/patterns/double-checked-locking/
public class DoubleCheckedLocking {

	public static void main(String[] args) {
		final Inventory inventory = new Inventory(1000);
	    ExecutorService executorService = Executors.newFixedThreadPool(3);
	    for (int i = 0; i < 3; i++) {
	      executorService.execute(() -> {
	        while (inventory.addItem(new Item())) {};
	      });
	    }

	    executorService.shutdown();
	    try {
	      executorService.awaitTermination(5, TimeUnit.SECONDS);
	    } catch (InterruptedException e) {
	      System.out.println("Error waiting for ExecutorService shutdown");
	    }
	}

}
