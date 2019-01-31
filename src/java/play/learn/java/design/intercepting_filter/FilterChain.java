package play.learn.java.design.intercepting_filter;

public class FilterChain {
	private Filter chain;

	/**
	 * Adds filter
	 */
	public void addFilter(Filter filter) {
		if (chain == null) {
			chain = filter;
		} else {
			chain.getLast().setNext(filter);
		}
	}

	/**
	 * Execute filter chain
	 */
	public String execute(Order order) {
		if (chain != null) {
			return chain.execute(order);
		} else {
			return "RUNNING...";
		}
	}
}
