package play.learn.java.design.semaphore;

public class Fruit {
	/**
	 * Enumeration of Fruit Types
	 */
	public static enum FruitType {
		ORANGE, APPLE, LEMON
	}

	private FruitType type;

	public Fruit(FruitType type) {
		this.type = type;
	}

	public FruitType getType() {
		return type;
	}

	/**
	 * toString method
	 */
	public String toString() {
		switch (type) {
		case ORANGE:
			return "Orange";
		case APPLE:
			return "Apple";
		case LEMON:
			return "Lemon";
		default:
			return "";
		}
	}

}
