package play.learn.java.design.feature_toggle;

import java.util.Properties;

// https://java-design-patterns.com/patterns/feature-toggle/
public class FeatureToggleDemo {

	  /**
	   *  Block 1 shows the {@link PropertiesFeatureToggleVersion} being run with {@link Properties} setting the feature
	   *  toggle to enabled.
	   *
	   *  Block 2 shows the {@link PropertiesFeatureToggleVersion} being run with {@link Properties} setting the feature
	   *  toggle to disabled. Notice the difference with the printed welcome message the username is not included.
	   *
	   *  Block 3 shows the {@link com.iluwatar.featuretoggle.pattern.tieredversion.TieredFeatureToggleVersion} being
	   *  set up with two users on who is on the free level, while the other is on the paid level. When the
	   *  {@link Service#getWelcomeMessage(User)} is called with the paid {@link User} note that the welcome message
	   *  contains their username, while the same service call with the free tier user is more generic. No username is
	   *  printed.
	   *
	   *  @see User
	   *  @see UserGroup
	   *  @see Service
	   *  @see PropertiesFeatureToggleVersion
	   *  @see com.iluwatar.featuretoggle.pattern.tieredversion.TieredFeatureToggleVersion
	   */
	  public static void main(String[] args) {

	    final Properties properties = new Properties();
	    properties.put("enhancedWelcome", true);
	    Service service = new PropertiesFeatureToggleVersion(properties);
	    final String welcomeMessage = service.getWelcomeMessage(new User("Jamie No Code"));
	    System.out.println(welcomeMessage);

	    // ---------------------------------------------

	    final Properties turnedOff = new Properties();
	    turnedOff.put("enhancedWelcome", false);
	    Service turnedOffService = new PropertiesFeatureToggleVersion(turnedOff);
	    final String welcomeMessageturnedOff = turnedOffService.getWelcomeMessage(new User("Jamie No Code"));
	    System.out.println(welcomeMessageturnedOff);

	    // --------------------------------------------
	    
	    Service service2 = new TieredFeatureToggleVersion();

	    final User paidUser = new User("Jamie Coder");
	    final User freeUser = new User("Alan Defect");

	    UserGroup.addUserToPaidGroup(paidUser);
	    UserGroup.addUserToFreeGroup(freeUser);

	    final String welcomeMessagePaidUser = service2.getWelcomeMessage(paidUser);
	    final String welcomeMessageFreeUser = service2.getWelcomeMessage(freeUser);
	    System.out.println(welcomeMessageFreeUser);
	    System.out.println(welcomeMessagePaidUser);
	  }

}
