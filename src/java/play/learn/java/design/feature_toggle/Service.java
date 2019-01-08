package play.learn.java.design.feature_toggle;

public interface Service {

	/**
	 * Generates a welcome message for the passed user.
	 *
	 * @param user
	 *            the {@link User} to be used if the message is to be personalised.
	 * @return Generated {@link String} welcome message
	 */
	String getWelcomeMessage(User user);

	/**
	 * Returns if the welcome message to be displayed will be the enhanced version.
	 *
	 * @return Boolean {@code true} if enhanced.
	 */
	boolean isEnhanced();

}
