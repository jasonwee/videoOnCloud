package play.learn.java.longaccumulator;

import java.util.concurrent.atomic.LongAccumulator;

public class LearnLongAccumulator {

	public static void main(String[] args) {
		LongAccumulator la = new LongAccumulator((long  x, long y) -> y - x, 0);
		System.out.println("pristine " + la.get());
		
		la.accumulate(10);
		
		System.out.println(la.get());

		la.accumulate(2);
		
		System.out.println(la.get());
		
		la.accumulate(-2);
		
		System.out.println(la.get());
		
		la.accumulate(1);
		
		System.out.println(la.get());
	}

}
