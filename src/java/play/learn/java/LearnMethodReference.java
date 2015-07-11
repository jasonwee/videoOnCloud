package play.learn.java;

import java.util.ArrayList;
import java.util.List;

/**
 * http://www.tutorialspoint.com/java8/java8_method_references.htm
 * 
 * @author jason
 *
 */
public class LearnMethodReference {

	public static void main(String[] args) {
		List<String> fruits = new ArrayList<String>();
		fruits.add("apple");
		fruits.add("orange");
		fruits.add("banana");
		fruits.add("durian");
		fruits.add("pineapple");
		fruits.add("watermelon");
		
		fruits.forEach(System.out::println);
		
	}

}
