package play.learn.java.design.chainofresponsibility;

public class PresidentPPower extends PurchasePower {

	@Override
	protected double getAllowable() {
		return BASE * 60;
	}

	@Override
	protected String getRole() {
		return "President";
	}

}
