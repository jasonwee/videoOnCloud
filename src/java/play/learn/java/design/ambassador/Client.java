package play.learn.java.design.ambassador;

public class Client {

	private final ServiceAmbassador serviceAmbassador = new ServiceAmbassador();

	long useService(int value) {
		long result = serviceAmbassador.doRemoteFunction(value);
		System.out.println("Service result: " + result);
		return result;
	}

}
