package play.learn.java.aspectj.customer;

public class CustomerImpl implements Customer {

	@Override
	public void addCustomer() {
		System.out.println("addCustomer() is running ");
	}

	@Override
	public String addCustomerReturnValue() {
		System.out.println("addCustomerReturnValue() is running ");
		return "abc";
	}

	@Override
	public void addCustomerThrowException() throws Exception {
		System.out.println("addCustomerThrowException() is running ");
		throw new Exception("Generic Error");
	}

	@Override
	public void addCustomerAround(String name) {
		System.out.println("addCustomerAround() is running, args : " + name);
	}

}
