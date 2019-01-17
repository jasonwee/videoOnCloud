package play.learn.java.design.service_locator;

public class ServiceImpl implements Service {

	private final String serviceName;
	private final int id;

	/**
	 * Constructor
	 */
	public ServiceImpl(String serviceName) {
		// set the service name
		this.serviceName = serviceName;

		// Generate a random id to this service object
		this.id = (int) Math.floor(Math.random() * 1000) + 1;
	}

	@Override
	public String getName() {
		return serviceName;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void execute() {
		System.out.printf("Service %s is now executing with id %s", getName(), getId());
	}

}
