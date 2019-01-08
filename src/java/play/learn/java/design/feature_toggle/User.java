package play.learn.java.design.feature_toggle;

public class User {
	  private String name;

	  /**
	   * Default Constructor setting the username.
	   *
	   * @param name {@link String} to represent the name of the user.
	   */
	  public User(String name) {
	    this.name = name;
	  }

	  /**
	   * {@inheritDoc}
	   * @return The {@link String} representation of the User, in this case just return the name of the user.
	   */
	  @Override
	  public String toString() {
	    return name;
	  }
}
