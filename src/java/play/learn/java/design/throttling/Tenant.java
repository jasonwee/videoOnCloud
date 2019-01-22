package play.learn.java.design.throttling;

import java.security.InvalidParameterException;

public class Tenant {

	private String name;
	private int allowedCallsPerSecond;

	/**
	 *
	 * @param name
	 *            Name of the tenant
	 * @param allowedCallsPerSecond
	 *            The number of calls allowed for a particular tenant.
	 * @throws InvalidParameterException
	 *             If number of calls is less than 0, throws exception.
	 */
	public Tenant(String name, int allowedCallsPerSecond, CallsCount callsCount) {
		if (allowedCallsPerSecond < 0) {
			throw new InvalidParameterException("Number of calls less than 0 not allowed");
		}
		this.name = name;
		this.allowedCallsPerSecond = allowedCallsPerSecond;
		callsCount.addTenant(name);
	}

	public String getName() {
		return name;
	}

	public int getAllowedCallsPerSecond() {
		return allowedCallsPerSecond;
	}

}
