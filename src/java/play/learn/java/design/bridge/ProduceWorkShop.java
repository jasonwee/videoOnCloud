package play.learn.java.design.bridge;

import java.util.concurrent.TimeUnit;

public class ProduceWorkShop extends WorkShop {
	
	public ProduceWorkShop() {
		super();
	}

	@Override
	public void work(Vehicle vehicle) {
		System.out.println("Producing... ");
		long timeToTake = 300 * vehicle.minWorkTime();
		try {
			TimeUnit.MILLISECONDS.sleep(timeToTake);
		} catch (InterruptedException e) {
			
		}
		System.out.printf("(Time taken: %d millis), Done.\n", timeToTake);

	}

}
