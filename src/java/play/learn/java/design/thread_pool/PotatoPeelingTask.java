package play.learn.java.design.thread_pool;

public class PotatoPeelingTask extends Task {

	private static final int TIME_PER_POTATO = 200;

	public PotatoPeelingTask(int numPotatoes) {
		super(numPotatoes * TIME_PER_POTATO);
	}

	@Override
	public String toString() {
		return String.format("%s %s", this.getClass().getSimpleName(), super.toString());
	}
}
