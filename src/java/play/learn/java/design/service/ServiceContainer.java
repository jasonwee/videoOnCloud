package play.learn.java.design.service;

import java.util.ArrayList;
import java.util.List;

public class ServiceContainer extends AbstractService {

	private List<Service> services = new ArrayList<Service>();

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public void addService(Service service) {
		this.services.add(service);
	}

	public void initService() {
		logger.debug("Initializing " + this + " with " + services.size() + " services.");
		for (Service service : services) {
			logger.debug("Initializing " + service);
			service.init();
		}
		logger.info(this + " inited.");
	}

	public void startService() {
		logger.debug("Starting " + this + " with " + services.size() + " services.");
		for (Service service : services) {
			logger.debug("Starting " + service);
			service.start();
		}
		logger.info(this + " started.");
	}

	public void stopService() {
		int size = services.size();
		logger.debug("Stopping " + this + " with " + size + " services in reverse order.");
		for (int i = size - 1; i >= 0; i--) {
			Service service = services.get(i);
			logger.debug("Stopping " + service);
			service.stop();
		}
		logger.info(this + " stopped.");
	}

	public void destroyService() {
		int size = services.size();
		logger.debug("Destroying " + this + " with " + size + " services in reverse order.");
		for (int i = size - 1; i >= 0; i--) {
			Service service = services.get(i);
			logger.debug("Destroying " + service);
			service.destroy();
		}
		logger.info(this + " destroyed.");
	}

}
