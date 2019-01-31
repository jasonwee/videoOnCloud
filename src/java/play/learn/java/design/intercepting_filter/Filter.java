package play.learn.java.design.intercepting_filter;

public interface Filter {
	/**
	 * Execute order processing filter.
	 */
	String execute(Order order);

	/**
	 * Set next filter in chain after this.
	 */
	void setNext(Filter filter);

	/**
	 * Get next filter in chain after this.
	 */
	Filter getNext();

	/**
	 * Get last filter in the chain.
	 */
	Filter getLast();
}
