package play.learn.java.design.intercepting_filter;

public class NameFilter extends AbstractFilter {

	@Override
	public String execute(Order order) {
		String result = super.execute(order);
		if (order.getName() == null || order.getName().isEmpty() || order.getName().matches(".*[^\\w|\\s]+.*")) {
			return result + "Invalid name! ";
		} else {
			return result;
		}
	}

}
