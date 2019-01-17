package play.learn.java.design.service_locator;

public interface Service {
	/*
	 * The human readable name of the service
	 */
	String getName();

	/*
	 * Unique ID of the particular service
	 */
	int getId();

	/*
	 * The workflow method that defines what this service does
	 */
	void execute();
}
