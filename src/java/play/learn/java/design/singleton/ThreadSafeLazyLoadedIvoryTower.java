package play.learn.java.design.singleton;

public final class ThreadSafeLazyLoadedIvoryTower {
	private static ThreadSafeLazyLoadedIvoryTower instance;

	private ThreadSafeLazyLoadedIvoryTower() {
		// protect against instantiation via reflection
		if (instance == null) {
			instance = this;
		} else {
			throw new IllegalStateException("Already initialized.");
		}
	}

	/**
	 * The instance gets created only when it is called for first time. Lazy-loading
	 */
	public static synchronized ThreadSafeLazyLoadedIvoryTower getInstance() {
		if (instance == null) {
			instance = new ThreadSafeLazyLoadedIvoryTower();
		}

		return instance;
	}

}
