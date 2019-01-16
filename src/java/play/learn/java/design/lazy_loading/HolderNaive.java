package play.learn.java.design.lazy_loading;

public class HolderNaive {

	private Heavy heavy;

	/**
	 * Constructor
	 */
	public HolderNaive() {
		System.out.println("HolderNaive created");
	}

	/**
	 * Get heavy object
	 */
	public Heavy getHeavy() {
		if (heavy == null) {
			heavy = new Heavy();
		}
		return heavy;
	}

}
