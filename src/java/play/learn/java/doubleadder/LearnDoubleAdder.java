package play.learn.java.doubleadder;

import java.util.concurrent.atomic.DoubleAdder;

public class LearnDoubleAdder {

	public static void main(String[] args) {
		DoubleAdder da = new DoubleAdder();
		System.out.println("initial value " + da.intValue());
		
		da.add(1);
		System.out.println("add 1 to da " + da.intValue());

		System.out.println("da byteValue " + da.byteValue());
		System.out.println("da doubleValue " + da.doubleValue());
		System.out.println("da floatValue " + da.floatValue());
		System.out.println("da longValue " + da.longValue());
		System.out.println("da shortValue " + da.shortValue());
		// better use sum() method below, in multi threaded environment.
		System.out.println("da sum " + da.sum());
		
		da.add(2);
		System.out.println("add 2 to da " + da.intValue());
		
		System.out.println("sum and then reset to da " + da.sumThenReset());
		
		System.out.println("after reset, da " + da.intValue());
	}

}
