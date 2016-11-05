package play.learn.java.design.chainofresponsibility;

public class ManagerPPower extends PurchasePower {

	@Override
	protected double getAllowable() {
		return BASE*10;
	}

	@Override
	protected String getRole() {
		return "Manager";
	}

}
