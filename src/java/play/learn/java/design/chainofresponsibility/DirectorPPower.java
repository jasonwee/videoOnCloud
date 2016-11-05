package play.learn.java.design.chainofresponsibility;

public class DirectorPPower extends PurchasePower {

	@Override
	protected double getAllowable() {
		return BASE * 20;
	}

	@Override
	protected String getRole() {
		return "Director";
	}

}
