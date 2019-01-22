package play.learn.java.design.template_method;

public abstract class StealingMethod {
	protected abstract String pickTarget();

	protected abstract void confuseTarget(String target);

	protected abstract void stealTheItem(String target);

	/**
	 * Steal
	 */
	public void steal() {
		String target = pickTarget();
		System.out.printf("The target has been chosen as %s.%n", target);
		confuseTarget(target);
		stealTheItem(target);
	}
}
