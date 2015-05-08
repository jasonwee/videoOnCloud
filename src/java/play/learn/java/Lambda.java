package play.learn.java;


import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;


//http://www.oracle.com/technetwork/articles/java/architect-lambdas-part1-2080972.html
//http://www.oracle.com/technetwork/articles/java/architect-lambdas-part2-2081439.html
public class Lambda {

	public Runnable r = () -> {
		System.out.println(this);
		System.out.println(toString());
	};
	
	public String toString() {
		return "Hello's custom toString()";
	}
	
	public static void main(String[] args) {
		
		// === primitive way   ===
		Runnable r = new Runnable() {
			@Override
			public void run() {
				System.out.println("Hello, world!");
			}
		};
		r.run();
		// === primitive way   ===
		
		// === lambda way   ===
		Runnable r1 = () -> System.out.println("Hello, world!");
		r1.run();
		/* Syntax. A lambda in Java essentially consists of three parts: a parenthesized 
		    set of parameters, an arrow, and then a body, which can either be a single 
		    expression or a block of Java code.
		    */
		
		Comparator<String> c = (String lhs, String rhs) -> lhs.compareTo(rhs);
		int result = c.compare("Hello", "World");
		
		// or
		
		c = (String lhs, String rhs) -> { 
			System.out.println("I am comparing lhs " + lhs + " to " + rhs);
			return lhs.compareTo(rhs);
			}; 
		result = c.compare("Hello", "World");
		
		Object o = (Runnable)() -> System.out.println("Hello world");
		

		Lambda h = new Lambda();
		h.r.run();
		
		List<Person> people = Arrays.asList(
			      new Person("Ted", "Neward", 42),
			      new Person("Charlotte", "Neward", 39),
			      new Person("Michael", "Neward", 19),
			      new Person("Matthew", "Neward", 13),
			      new Person("Neal", "Ford", 45),
			      new Person("Candy", "Ford", 39),
			      new Person("Jeff", "Brown", 43),
			      new Person("Betsy", "Brown", 39)
		);
	
		Collections.sort(people, (lhs, rhs) -> {
				if (lhs.getLastName().equals(rhs.getLastName())) {
					return lhs.getAge() - rhs.getAge();
				} else {
					return lhs.getLastName().compareTo(rhs.getLastName());
				}
		});
		
		Collections.sort(people, Person.BY_LAST_AND_AGE);
		Collections.sort(people, Person::compareLastAndAge);
		
		 Collections.sort(people, Person.BY_LAST.thenComparing(Person.BY_AGE)); 
		
		 people.stream().filter(it -> it.getAge() >= 21).forEach((it)->System.out.println("Have a beer, " + it.getFirstName()));;
		 
		 System.out.println("---");
		 
		 Predicate<Person> drinkingAge = (it) -> it.getAge() >= 21;
		    Predicate<Person> brown = (it) -> it.getLastName().equals("Brown");
		    people.stream()
		      .filter(drinkingAge.and(brown))
		      .forEach((it) ->
		                System.out.println("Have a beer, " +
		                                   it.getFirstName()));
		    
		    int sum = people.stream()
	                .mapToInt(Person::getAge)
	                .sum(); 
		    System.out.println("sum age of all people " + sum);
		    
		    String xml =
		    	      "<people data='lastname'>" +
		    	      people.stream()
		    	            .map(it -> "<person>" + it.getLastName() + "</person>")
		    	            .reduce("", String::concat)
		    	      + "</people>";
		    	    System.out.println(xml); 
		    	    
		    	    String json =
		    	    	      people.stream()
		    	    	        .map(Person::toJSON)
		    	    	        .reduce("[", (l, re) -> l + (l.equals("[") ? "" : ",") + re)
		    	    	        + "]";
		    	    	    System.out.println(json);

		people.parallelStream()
				.filter((it) -> it.getAge() >= 21)
				.forEach(
						(it) -> System.out.println("Have a beer "
								+ it.getFirstName() + Thread.currentThread()));
		// === lambda way   ===
	}
	

}
