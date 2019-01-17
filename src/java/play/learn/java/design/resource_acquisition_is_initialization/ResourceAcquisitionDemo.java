package play.learn.java.design.resource_acquisition_is_initialization;

// https://java-design-patterns.com/patterns/resource-acquisition-is-initialization/
public class ResourceAcquisitionDemo {

	public static void main(String[] args) throws Exception {

		try (SlidingDoor slidingDoor = new SlidingDoor()) {
			System.out.println("Walking in.");
		}

		try (TreasureChest treasureChest = new TreasureChest()) {
			System.out.println("Looting contents.");
		}
	}
}
