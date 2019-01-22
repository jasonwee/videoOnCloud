package play.learn.java.design.template_method;

public class HalflingThief {
	private StealingMethod method;

	public HalflingThief(StealingMethod method) {
		this.method = method;
	}

	public void steal() {
		method.steal();
	}

	public void changeMethod(StealingMethod method) {
		this.method = method;
	}
}
