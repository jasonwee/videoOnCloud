package play.learn.java.design.semaphore;

// https://java-design-patterns.com/patterns/semaphore/
public class SemaphoreDemo {

	public static void main(String[] args) {
		FruitShop shop = new FruitShop();
		new Customer("Peter", shop).start();
		new Customer("Paul", shop).start();
		new Customer("Mary", shop).start();
		new Customer("John", shop).start();
		new Customer("Ringo", shop).start();
		new Customer("George", shop).start();
	}

}
