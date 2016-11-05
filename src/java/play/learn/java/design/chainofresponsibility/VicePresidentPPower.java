package play.learn.java.design.chainofresponsibility;

public class VicePresidentPPower extends PurchasePower {

	@Override
	protected double getAllowable() {
		return BASE*40;
	}

	@Override
	protected String getRole() {
		return "Vice President";
	}

}
