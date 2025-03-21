package play.learn.java.design.intercepting_filter;

public class Order {
	private String name;
	private String contactNumber;
	private String address;
	private String depositNumber;
	private String orderItem;

	public Order() {
	}

	/**
	 * Constructor
	 */
	public Order(String name, String contactNumber, String address, String depositNumber, String order) {
		this.name = name;
		this.contactNumber = contactNumber;
		this.address = address;
		this.depositNumber = depositNumber;
		this.orderItem = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDepositNumber() {
		return depositNumber;
	}

	public void setDepositNumber(String depositNumber) {
		this.depositNumber = depositNumber;
	}

	public String getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(String order) {
		this.orderItem = order;
	}
}
