package play.learn.java.design.intercepting_filter;

// https://java-design-patterns.com/patterns/intercepting-filter/
public class InterceptingFilterDemo {

	public static void main(String[] args) {
		FilterManager filterManager = new FilterManager();
		filterManager.addFilter(new NameFilter());
		filterManager.addFilter(new ContactFilter());
		filterManager.addFilter(new AddressFilter());
		filterManager.addFilter(new DepositFilter());
		filterManager.addFilter(new OrderFilter());

		Client client = new Client();
		client.setFilterManager(filterManager);
	}

}
