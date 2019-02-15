package play.learn.java.design.retry;

public class BusinessException extends Exception {
	private static final long serialVersionUID = 6235833142062144336L;

	/**
	 * Ctor
	 * 
	 * @param message
	 *            the error message
	 */
	public BusinessException(String message) {
		super(message);
	}
}
