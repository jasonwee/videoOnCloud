package play.learn.java.design.template_method;

public class SubtleMethod extends StealingMethod {

	@Override
	protected String pickTarget() {
		return "shop keeper";
	}

	@Override
	protected void confuseTarget(String target) {
		System.out.printf("Approach the %s with tears running and hug him!%n", target);
	}

	@Override
	protected void stealTheItem(String target) {
		System.out.printf("While in close contact grab the %s's wallet.%n", target);
	}

}
