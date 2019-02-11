package play.learn.java.design.state;

// https://java-design-patterns.com/patterns/state/
public class StateDemo {
	public static void main(String[] args) {

		Mammoth mammoth = new Mammoth();
		mammoth.observe();
		mammoth.timePasses();
		mammoth.observe();
		mammoth.timePasses();
		mammoth.observe();

	}
}
