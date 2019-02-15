package play.learn.java.design.retry;

public class CustomerNotFoundException extends BusinessException {
	private static final long serialVersionUID = -6972888602621778664L;

	/**
	 * Ctor.
	 * 
	 * @param message
	 *            the error message
	 */
	public CustomerNotFoundException(String message) {
		super(message);
	}
}
