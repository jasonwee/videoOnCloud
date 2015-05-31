package play.learn.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;

public class LearnIterable {

	public static void main(String[] args) {
		
		Integer[] intIntegers = {1,2,3,4,5};
		List<Integer> integers = new ArrayList<Integer>(Arrays.asList(intIntegers));
		
		// added in java8
		integers.forEach((x) -> {System.out.println(x+1);});
		
		System.out.println(integers.toString());
		
		Spliterator se = integers.spliterator();
		System.out.println(se.estimateSize());
		

	}

}
