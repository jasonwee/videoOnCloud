package play.learn.java;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * http://www.tutorialspoint.com/java8/java8_functional_interfaces.htm
 * 
 * @author jason
 *
 */
public class LearnFunctionalInterface {
	
	public static void eval(List<Integer> list, Predicate<Integer> predicate) {
		for (Integer n : list) {
			if (predicate.test(n)) {
				System.out.println(n + " ");
			}
		}
	}

	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9);
		
		// Predicate<Integer> predicate = n -> true
	    // n is passed as parameter to test method of Predicate interface
	    // test method will always return true no matter what value n has.
		//pass n as parameter 
		eval(list, n->true);

		eval(list, n -> n % 2 == 0);
		
		eval(list, n-> n > 3);
	}
	

}
