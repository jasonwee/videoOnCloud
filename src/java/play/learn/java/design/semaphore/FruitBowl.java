package play.learn.java.design.semaphore;

import java.util.List;
import java.util.ArrayList;

public class FruitBowl {
	private List<Fruit> fruit = new ArrayList<>();

	/**
	 * 
	 * @return The amount of Fruit left in the bowl.
	 */
	public int countFruit() {
		return fruit.size();
	}

	/**
	 * Put an item of Fruit into the bowl.
	 * 
	 * @param f
	 *            fruit
	 */
	public void put(Fruit f) {
		fruit.add(f);
	}

	/**
	 * Take an item of Fruit out of the bowl.
	 * 
	 * @return The Fruit taken out of the bowl, or null if empty.
	 */
	public Fruit take() {
		if (fruit.isEmpty()) {
			return null;
		} else {
			return fruit.remove(0);
		}
	}

	/**
	 * toString method
	 */
	public String toString() {
		int apples = 0;
		int oranges = 0;
		int lemons = 0;

		for (Fruit f : fruit) {
			switch (f.getType()) {
			case APPLE:
				apples++;
				break;
			case ORANGE:
				oranges++;
				break;
			case LEMON:
				lemons++;
				break;
			default:
			}
		}

		return apples + " Apples, " + oranges + " Oranges, and " + lemons + " Lemons";
	}

}
