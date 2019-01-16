package play.learn.java.design.lazy_loading;

public class Heavy {

	/**
	 * Constructor
	 */
	public Heavy() {
		System.out.println("Creating Heavy ...");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("... Heavy created");

	}

}
