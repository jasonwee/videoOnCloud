package play.learn.java.design.intercepting_filter;

public class OrderFilter extends AbstractFilter {
	@Override
	public String execute(Order order) {
		String result = super.execute(order);
		if (order.getOrderItem() == null || order.getOrderItem().isEmpty()) {
			return result + "Invalid order! ";
		} else {
			return result;
		}
	}

}
