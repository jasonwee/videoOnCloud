package play.learn.java.design.semaphore;

public class Customer extends Thread {

	/**
	 * Name of the Customer.
	 */
	private final String name;

	/**
	 * The FruitShop he is using.
	 */
	private final FruitShop fruitShop;

	/**
	 * Their bowl of Fruit.
	 */
	private final FruitBowl fruitBowl;

	/**
	 * Customer constructor
	 */
	public Customer(String name, FruitShop fruitShop) {
		this.name = name;
		this.fruitShop = fruitShop;
		this.fruitBowl = new FruitBowl();
	}

	/**
	 * The Customer repeatedly takes Fruit from the FruitShop until no Fruit
	 * remains.
	 */
	public void run() {

		while (fruitShop.countFruit() > 0) {
			FruitBowl bowl = fruitShop.takeBowl();
			Fruit fruit;

			if (bowl != null && (fruit = bowl.take()) != null) {
				System.out.printf("%s took an %s", name, fruit);
				fruitBowl.put(fruit);
				fruitShop.returnBowl(bowl);
			}
		}

		System.out.printf("%s took %s", name, fruitBowl);

	}

}
