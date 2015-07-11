package play.learn.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * http://www.tutorialspoint.com/java8/java8_overview.htm
 * 
 * @author jason
 *
 */
public class LearnLambda {

	public static void main(String[] args) {
		List<String> fruits1 = new ArrayList<String>();
		fruits1.add("apple");
		fruits1.add("orange");
		fruits1.add("banana");
		fruits1.add("durian");
		fruits1.add("pineapple");
		fruits1.add("watermelon");
		
		List<String> fruits2 = new ArrayList<String>();
		fruits2.add("apple");
		fruits2.add("orange");
		fruits2.add("banana");
		fruits2.add("durian");
		fruits2.add("pineapple");
		fruits2.add("watermelon");

		  LearnLambda tester = new LearnLambda();

	      System.out.println("Sort using Java 7 syntax: ");      
	      tester.sortUsingJava7(fruits1);
	      System.out.println(fruits1);     
	   
	      System.out.println("Sort using Java 8 syntax: ");      
	      tester.sortUsingJava8(fruits2);
	      System.out.println(fruits2);
	}
	
	   private void sortUsingJava7(List<String> fruits){
		      //sort using java 7
		      Collections.sort(fruits, new Comparator<String>() {
		         @Override
		         public int compare(String s1, String s2) {
		            return s1.compareTo(s2);
		         }
		      });
		   }
		   private void sortUsingJava8(List<String> fruits){
		      //sort using java 8
		      Collections.sort(fruits, (s1, s2) ->  s1.compareTo(s2));      
		   }

}

