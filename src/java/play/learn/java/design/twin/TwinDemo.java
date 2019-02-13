package play.learn.java.design.twin;

// https://java-design-patterns.com/patterns/twin/
public class TwinDemo {

	/**
	 * Program entry point
	 * 
	 * @param args
	 *            command line args
	 */
	public static void main(String[] args) throws Exception {

		BallItem ballItem = new BallItem();
		BallThread ballThread = new BallThread();

		ballItem.setTwin(ballThread);
		ballThread.setTwin(ballItem);

		ballThread.start();

		waiting();

		ballItem.click();

		waiting();

		ballItem.click();

		waiting();

		// exit
		ballThread.stopMe();
	}

	private static void waiting() throws Exception {
		Thread.sleep(750);
	}

}
