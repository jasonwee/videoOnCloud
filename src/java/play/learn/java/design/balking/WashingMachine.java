package play.learn.java.design.balking;

import java.util.concurrent.TimeUnit;

// https://java-design-patterns.com/patterns/balking/
// balk : hesitate or be unwilling to accept an idea or undertaking.
public class WashingMachine {
	
	private final DelayProvider delayProvider;
	private WashingMachineState washingMachineState;
	
	public WashingMachine() {
		this((interval, timeUnit, task) -> {
			try {
				Thread.sleep(timeUnit.toMillis(interval));
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
			task.run();
		});
	}
	
	public WashingMachine(DelayProvider delayProvider) {
		this.delayProvider = delayProvider;
		this.washingMachineState = WashingMachineState.ENABLED;
	}
	
	public WashingMachineState getWashingMachineState() {
		return washingMachineState;
	}
	
	public void wash() {
		synchronized (this) {
		      System.out.format("%s: Actual machine state: %s %n", Thread.currentThread().getName(), getWashingMachineState());
		      if (washingMachineState == WashingMachineState.WASHING) {
		        System.err.println("ERROR: Cannot wash if the machine has been already washing!");
		        return;
		      }
		      washingMachineState = WashingMachineState.WASHING;
		}
		
		System.out.format("%s: Doing the washing %n", Thread.currentThread().getName());

	    this.delayProvider.executeAfterDelay(50, TimeUnit.MILLISECONDS, this::endOfWashing);
	}
	
	public synchronized void endOfWashing() {
		washingMachineState = WashingMachineState.ENABLED;
	    System.out.format("%s: Washing completed. %n", Thread.currentThread().getId());

	}

}
