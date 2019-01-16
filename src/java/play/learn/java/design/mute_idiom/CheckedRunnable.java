package play.learn.java.design.mute_idiom;

@FunctionalInterface
public interface CheckedRunnable {

	/**
	 * Same as {@link Runnable#run()} with a possibility of exception in execution.
	 * 
	 * @throws Exception
	 *             if any exception occurs.
	 */
	void run() throws Exception;
}
