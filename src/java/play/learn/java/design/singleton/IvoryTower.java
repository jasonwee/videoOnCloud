package play.learn.java.design.singleton;

public final class IvoryTower {
	/**
	 * Private constructor so nobody can instantiate the class.
	 */
	private IvoryTower() {
	}

	/**
	 * Static to class instance of the class.
	 */
	private static final IvoryTower INSTANCE = new IvoryTower();

	/**
	 * To be called by user to obtain instance of the class.
	 *
	 * @return instance of the singleton.
	 */
	public static IvoryTower getInstance() {
		return INSTANCE;
	}
}
