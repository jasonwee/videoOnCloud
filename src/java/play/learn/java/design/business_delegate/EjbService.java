package play.learn.java.design.business_delegate;

public class EjbService implements BusinessService {

	@Override
	public void doProcessing() {
		System.out.println("EjbService is now processing");
	}
}
