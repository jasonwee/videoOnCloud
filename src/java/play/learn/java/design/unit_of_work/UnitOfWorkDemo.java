package play.learn.java.design.unit_of_work;

import java.util.HashMap;
import java.util.List;

// https://java-design-patterns.com/patterns/unit-of-work/
public class UnitOfWorkDemo {
	public static void main(String[] args) {
		Student ram = new Student(1, "Ram", "Street 9, Cupertino");
		Student shyam = new Student(2, "Shyam", "Z bridge, Pune");
		Student gopi = new Student(3, "Gopi", "Street 10, Mumbai");

		HashMap<String, List<Student>> context = new HashMap<>();
		StudentDatabase studentDatabase = new StudentDatabase();
		StudentRepository studentRepository = new StudentRepository(context, studentDatabase);

		studentRepository.registerNew(ram);
		studentRepository.registerModified(shyam);
		studentRepository.registerDeleted(gopi);
		studentRepository.commit();
	}
}
