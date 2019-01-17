package play.learn.java.design.service_locator;

import java.util.HashMap;
import java.util.Map;

public class ServiceCache {

	private final Map<String, Service> serviceCache;

	public ServiceCache() {
		serviceCache = new HashMap<>();
	}

	/**
	 * Get the service from the cache. null if no service is found matching the name
	 *
	 * @param serviceName
	 *            a string
	 * @return {@link Service}
	 */
	public Service getService(String serviceName) {
		Service cachedService = null;
		for (String serviceJndiName : serviceCache.keySet()) {
			if (serviceJndiName.equals(serviceName)) {
				cachedService = serviceCache.get(serviceJndiName);
				System.out.printf("(cache call) Fetched service {}({}) from cache... !", cachedService.getName(),
						cachedService.getId());
			}
		}
		return cachedService;
	}

	/**
	 * Adds the service into the cache map
	 *
	 * @param newService
	 *            a {@link Service}
	 */
	public void addService(Service newService) {
		serviceCache.put(newService.getName(), newService);
	}
}
