package play.learn.java.design.business_delegate;

// https://java-design-patterns.com/patterns/business-delegate/
public class BusinessDelegateDemo {
	public static void main(String[] args) {

		BusinessDelegate businessDelegate = new BusinessDelegate();
		BusinessLookup businessLookup = new BusinessLookup();
		businessLookup.setEjbService(new EjbService());
		businessLookup.setJmsService(new JmsService());

		businessDelegate.setLookupService(businessLookup);
		businessDelegate.setServiceType(ServiceType.EJB);

		Client client = new Client(businessDelegate);
		client.doTask();

		businessDelegate.setServiceType(ServiceType.JMS);
		client.doTask();
	}
}
