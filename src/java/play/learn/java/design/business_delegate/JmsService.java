package play.learn.java.design.business_delegate;

public class JmsService implements BusinessService {

	@Override
	public void doProcessing() {
		System.out.println("JmsService is now processing");
	}

}
