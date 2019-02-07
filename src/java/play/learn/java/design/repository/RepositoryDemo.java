package play.learn.java.design.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.context.support.ClassPathXmlApplicationContext;

// https://java-design-patterns.com/patterns/repository/
public class RepositoryDemo {
	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		PersonRepository repository = context.getBean(PersonRepository.class);

		Person peter = new Person("Peter", "Sagan", 17);
		Person nasta = new Person("Nasta", "Kuzminova", 25);
		Person john = new Person("John", "lawrence", 35);
		Person terry = new Person("Terry", "Law", 36);

		// Add new Person records
		repository.save(peter);
		repository.save(nasta);
		repository.save(john);
		repository.save(terry);

		// Count Person records
		System.out.printf("Count Person records: %s", repository.count());

		// Print all records
		List<Person> persons = (List<Person>) repository.findAll();
		for (Person person : persons) {
			System.out.println(person.toString());
		}

		// Update Person
		nasta.setName("Barbora");
		nasta.setSurname("Spotakova");
		repository.save(nasta);

		System.out.printf("Find by id 2: %s", repository.findOne(2L));

		// Remove record from Person
		repository.delete(2L);

		// count records
		System.out.printf("Count Person records: %s", repository.count());

		// find by name
		Optional<Person> p = repository.findOne(new PersonSpecifications.NameEqualSpec("John"));
		System.out.printf("Find by John is %s", p);

		// find by age
		persons = repository.findAll(new PersonSpecifications.AgeBetweenSpec(20, 40));

		System.out.println("Find Person with age between 20,40: ");
		for (Person person : persons) {
			System.out.println(person.toString());
		}

		repository.deleteAll();

		context.close();

	}
}
