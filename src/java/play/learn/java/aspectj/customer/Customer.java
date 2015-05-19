package play.learn.java.aspectj.customer;

public interface Customer {
	
	void addCustomer();
	
	String addCustomerReturnValue();
	
	void addCustomerThrowException() throws Exception;
	
	void addCustomerAround(String name);	
}
