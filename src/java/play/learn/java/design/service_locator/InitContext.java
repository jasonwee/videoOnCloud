package play.learn.java.design.service_locator;

public class InitContext {
	/**
	 * Perform the lookup based on the service name. The returned object will need
	 * to be casted into a {@link Service}
	 *
	 * @param serviceName
	 *            a string
	 * @return an {@link Object}
	 */
	public Object lookup(String serviceName) {
		if (serviceName.equals("jndi/serviceA")) {
			System.out.println("Looking up service A and creating new service for A");
			return new ServiceImpl("jndi/serviceA");
		} else if (serviceName.equals("jndi/serviceB")) {
			System.out.println("Looking up service B and creating new service for B");
			return new ServiceImpl("jndi/serviceB");
		} else {
			return null;
		}
	}
}
