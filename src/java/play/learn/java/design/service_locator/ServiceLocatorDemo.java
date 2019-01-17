package play.learn.java.design.service_locator;

// https://java-design-patterns.com/patterns/service-locator/
public class ServiceLocatorDemo {

	public static void main(String[] args) {
		Service service = ServiceLocator.getService("jndi/serviceA");
		service.execute();
		service = ServiceLocator.getService("jndi/serviceB");
		service.execute();
		service = ServiceLocator.getService("jndi/serviceA");
		service.execute();
		service = ServiceLocator.getService("jndi/serviceA");
		service.execute();
	}

}
