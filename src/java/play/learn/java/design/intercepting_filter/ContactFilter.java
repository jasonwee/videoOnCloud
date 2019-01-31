package play.learn.java.design.intercepting_filter;

public class ContactFilter extends AbstractFilter {

	@Override
	public String execute(Order order) {
		String result = super.execute(order);
		if (order.getContactNumber() == null || order.getContactNumber().isEmpty()
				|| order.getContactNumber().matches(".*[^\\d]+.*") || order.getContactNumber().length() != 11) {
			return result + "Invalid contact number! ";
		} else {
			return result;
		}
	}

}
