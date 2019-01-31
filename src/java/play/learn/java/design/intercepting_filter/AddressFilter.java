package play.learn.java.design.intercepting_filter;

public class AddressFilter extends AbstractFilter {

	@Override
	public String execute(Order order) {
		String result = super.execute(order);
		if (order.getAddress() == null || order.getAddress().isEmpty()) {
			return result + "Invalid address! ";
		} else {
			return result;
		}
	}

}
