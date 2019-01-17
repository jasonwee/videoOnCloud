package play.learn.java.design.resource_acquisition_is_initialization;

public class SlidingDoor implements AutoCloseable {

	public SlidingDoor() {
		System.out.println("Sliding door opens.");
	}

	@Override
	public void close() throws Exception {
		System.out.println("Sliding door closes.");
	}
}
