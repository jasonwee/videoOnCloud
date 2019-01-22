package play.learn.java.design.throttling;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class CallsCount {

	private Map<String, AtomicLong> tenantCallsCount = new ConcurrentHashMap<>();

	/**
	 * Add a new tenant to the map.
	 * 
	 * @param tenantName
	 *            name of the tenant.
	 */
	public void addTenant(String tenantName) {
		tenantCallsCount.putIfAbsent(tenantName, new AtomicLong(0));
	}

	/**
	 * Increment the count of the specified tenant.
	 * 
	 * @param tenantName
	 *            name of the tenant.
	 */
	public void incrementCount(String tenantName) {
		tenantCallsCount.get(tenantName).incrementAndGet();
	}

	/**
	 * 
	 * @param tenantName
	 *            name of the tenant.
	 * @return the count of the tenant.
	 */
	public long getCount(String tenantName) {
		return tenantCallsCount.get(tenantName).get();
	}

	/**
	 * Resets the count of all the tenants in the map.
	 */
	public void reset() {
		System.out.println("Resetting the map.");
		for (Entry<String, AtomicLong> e : tenantCallsCount.entrySet()) {
			tenantCallsCount.put(e.getKey(), new AtomicLong(0));
		}
	}
}
