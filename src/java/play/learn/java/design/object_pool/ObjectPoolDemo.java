package play.learn.java.design.object_pool;

// https://java-design-patterns.com/patterns/object-pool/
public class ObjectPoolDemo {

	public static void main(String[] args) {
		OliphauntPool pool = new OliphauntPool();
		System.out.println(pool.toString());
		Oliphaunt oliphaunt1 = pool.checkOut();
		System.out.printf("Checked out %s", oliphaunt1);
		System.out.println(pool.toString());
		Oliphaunt oliphaunt2 = pool.checkOut();
		System.out.printf("Checked out %s", oliphaunt2);
		Oliphaunt oliphaunt3 = pool.checkOut();
		System.out.printf("Checked out %s", oliphaunt3);
		System.out.println(pool.toString());
		System.out.printf("Checking in %s", oliphaunt1);
		pool.checkIn(oliphaunt1);
		System.out.printf("Checking in %s", oliphaunt2);
		pool.checkIn(oliphaunt2);
		System.out.println(pool.toString());
		Oliphaunt oliphaunt4 = pool.checkOut();
		System.out.printf("Checked out %s", oliphaunt4);
		Oliphaunt oliphaunt5 = pool.checkOut();
		System.out.printf("Checked out %s", oliphaunt5);
		System.out.println(pool.toString());
	}

}
