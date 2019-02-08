package play.learn.java.design.semaphore;

public class FruitShop {
	/**
	 * The FruitBowl instances stored in the class.
	 */
	private FruitBowl[] bowls = { new FruitBowl(), new FruitBowl(), new FruitBowl() };

	/**
	 * Access flags for each of the FruitBowl instances.
	 */
	private boolean[] available = { true, true, true };

	/**
	 * The Semaphore that controls access to the class resources.
	 */
	private Semaphore semaphore;

	/**
	 * FruitShop constructor
	 */
	public FruitShop() {
		for (int i = 0; i < 100; i++) {
			bowls[0].put(new Fruit(Fruit.FruitType.APPLE));
			bowls[1].put(new Fruit(Fruit.FruitType.ORANGE));
			bowls[2].put(new Fruit(Fruit.FruitType.LEMON));
		}

		semaphore = new Semaphore(3);
	}

	/**
	 * 
	 * @return The amount of Fruit left in the shop.
	 */
	public synchronized int countFruit() {
		return bowls[0].countFruit() + bowls[1].countFruit() + bowls[2].countFruit();
	}

	/**
	 * Method called by Customer to get a FruitBowl from the shop. This method will
	 * try to acquire the Semaphore before returning the first available FruitBowl.
	 */
	public synchronized FruitBowl takeBowl() {

		FruitBowl bowl = null;

		try {
			semaphore.acquire();

			if (available[0]) {
				bowl = bowls[0];
				available[0] = false;
			} else if (available[1]) {
				bowl = bowls[1];
				available[1] = false;
			} else if (available[2]) {
				bowl = bowls[2];
				available[2] = false;
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}
		return bowl;
	}

	/**
	 * Method called by a Customer instance to return a FruitBowl to the shop. This
	 * method releases the Semaphore, making the FruitBowl available to another
	 * Customer.
	 */
	public synchronized void returnBowl(FruitBowl bowl) {
		if (bowl == bowls[0]) {
			available[0] = true;
		} else if (bowl == bowls[1]) {
			available[1] = true;
		} else if (bowl == bowls[2]) {
			available[2] = true;
		}
	}
}
