package play.learn.java;

import java.util.Comparator;

import org.codehaus.jackson.map.util.Comparators;

public class Person {
	
	String firstName; 
	String lastName;
	int age;
	
	public Person(String fn, String ln, int a) {
		firstName = fn;
		lastName = ln;
		age = a;
	}
	
	public String getFirstName() { return firstName; }
	public String getLastName() { return lastName; }
	public int getAge() { return age; }
	
	public static final Comparator<Person> BY_LAST_AND_AGE = 
			(lhs, rhs) -> { 
				if (lhs.lastName.equals(rhs.lastName))
					return lhs.age - rhs.age;
				else
					return lhs.lastName.compareTo(rhs.lastName);
			};
			
	public static final Comparator<Person> BY_FIRST = (lhs, rhs) -> lhs.firstName.compareTo(rhs.firstName);
	public static final Comparator<Person> BY_LAST = (lhs, rhs) -> lhs.lastName.compareTo(rhs.lastName);
	public static final Comparator<Person> BY_AGE = (lhs, rhs) -> lhs.age - rhs.age; 


			  
	public static int compareLastAndAge(Person lhs, Person rhs) {
		if (lhs.lastName.equals(rhs.lastName))
			return lhs.age - rhs.age;
		else
			return lhs.lastName.compareTo(rhs.lastName);
	}
	
	 public static String toJSON(Person p) {
		    return
		      "{" +
		        "firstName: \"" + p.firstName + "\", " +
		        "lastName: \"" + p.lastName + "\", " +
		        "age: " + p.age + " " +
		      "}";
		  }

}
