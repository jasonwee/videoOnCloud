package play.learn.java.aspectj.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import play.learn.java.aspectj.customer.Customer;

/**
 * http://www.mkyong.com/spring3/spring-aop-aspectj-annotation-example/
 *
 */
public class MainApp {

	public static void main(String[] args) throws Exception {
		
		ApplicationContext appContext = new ClassPathXmlApplicationContext("Spring-Customer.xml");
		
		Customer customer = (Customer) appContext.getBean("custom");
		
		customer.addCustomer();
		
		customer.addCustomerReturnValue();
		
		customer.addCustomerThrowException();
		
		customer.addCustomerAround("mkyong");

	}

}
