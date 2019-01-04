package play.learn.java.design.dto;

import java.util.ArrayList;
import java.util.List;

// https://java-design-patterns.com/patterns/data-transfer-object/
public class CustomerClientApp {

	  /**
	   * Method as act client and request to server for details.
	   *
	   * @param args program argument.
	   */
	  public static void main(String[] args) {
	    List<CustomerDto> customers = new ArrayList<>();
	    CustomerDto customerOne = new CustomerDto("1", "Kelly", "Brown");
	    CustomerDto customerTwo = new CustomerDto("2", "Alfonso", "Bass");
	    customers.add(customerOne);
	    customers.add(customerTwo);

	    CustomerResource customerResource = new CustomerResource(customers);

	    System.out.println("All customers:-");
	    List<CustomerDto> allCustomers = customerResource.getAllCustomers();
	    printCustomerDetails(allCustomers);

	    System.out.println("----------------------------------------------------------");

	    System.out.println("Deleting customer with id {1}");
	    customerResource.delete(customerOne.getId());
	    allCustomers = customerResource.getAllCustomers();
	    printCustomerDetails(allCustomers);

	    System.out.println("----------------------------------------------------------");

	    System.out.println("Adding customer three}");
	    CustomerDto customerThree = new CustomerDto("3", "Lynda", "Blair");
	    customerResource.save(customerThree);
	    allCustomers = customerResource.getAllCustomers();
	    printCustomerDetails(allCustomers);
	  }

	  private static void printCustomerDetails(List<CustomerDto> allCustomers) {
	    allCustomers.forEach(customer -> System.out.println(customer.getFirstName()));
	    
	  }

}
