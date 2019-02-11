package play.learn.java.design.state;

public class PeacefulState implements State {
	private Mammoth mammoth;

	public PeacefulState(Mammoth mammoth) {
		this.mammoth = mammoth;
	}

	@Override
	public void observe() {
		System.out.printf("%s is calm and peaceful.", mammoth);
	}

	@Override
	public void onEnterState() {
		System.out.printf("%s calms down.", mammoth);
	}
}
