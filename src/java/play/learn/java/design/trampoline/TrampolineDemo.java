package play.learn.java.design.trampoline;

// https://java-design-patterns.com/patterns/trampoline/
public class TrampolineDemo {
	
	/**
	 * Main program for showing pattern. It does loop with factorial function.
	 */
	public static void main(String[] args) {
		System.out.println("start pattern");
		Integer result = loop(10, 1).result();
		System.out.printf("result %s", result);

	}

	/**
	 * Manager for pattern. Define it with a factorial function.
	 */
	public static Trampoline<Integer> loop(int times, int prod) {
		if (times == 0) {
			return Trampoline.done(prod);
		} else {
			return Trampoline.more(() -> loop(times - 1, prod * times));
		}
	}
}
