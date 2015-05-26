package play.learn.java.doubleaccumulator;

import java.util.concurrent.atomic.DoubleAccumulator;

public class LearnDoubleAccumulator {

	public static void main(String[] args) {
		DoubleAccumulator da = new DoubleAccumulator((double x, double y) -> x + y, 0);
		da = new DoubleAccumulator((double x, double y) -> x+y, 1);
		da.accumulate(1);
		da.accumulate(2);
		da.accumulate(3);
		
		System.out.println(da.get());
		System.out.println(da.doubleValue());
		System.out.println(da.floatValue());
		System.out.println(da.intValue());
		System.out.println(da.longValue());
		System.out.println(da.shortValue());
		
		MathOperation res = (int a, int b) -> a + b ;
		System.out.println(res.addition(1, 2));
	}
	
	interface MathOperation {
		int addition(int a, int b);
	}

}
