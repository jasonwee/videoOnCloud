package play.learn.java.longadder;

import java.util.concurrent.atomic.LongAdder;

public class LearnLongAdder {

	public static void main(String[] args) {
		LongAdder la = new LongAdder();
		la.add(1);
		la.add(2);
		la.add(3);
		System.out.println(la.intValue());
		
		la.decrement();
		System.out.println(la.intValue());
		
		la.increment();
		System.out.println(la.intValue());
		
		la.sumThenReset();
		System.out.println(la.intValue());
	}

}
