package play.learn.java.design.datamapper;

import java.util.Optional;

// https://github.com/iluwatar/java-design-patterns/tree/master/data-mapper
public class DataMapperDemo {

	 public static void main(final String... args) {

		    /* Create new data mapper for type 'first' */
		    final StudentDataMapper mapper = new StudentDataMapperImpl();

		    /* Create new student */
		    Student student = new Student(1, "Adam", 'A');

		    /* Add student in respectibe store */
		    mapper.insert(student);

		    System.out.println("App.main(), student : " + student + ", is inserted");

		    /* Find this student */
		    final Optional<Student> studentToBeFound = mapper.find(student.getStudentId());

		    System.out.println("App.main(), student : " + studentToBeFound + ", is searched");

		    /* Update existing student object */
		    student = new Student(student.getStudentId(), "AdamUpdated", 'A');

		    /* Update student in respectibe db */
		    mapper.update(student);

		    System.out.println("App.main(), student : " + student + ", is updated");
		    System.out.println("App.main(), student : " + student + ", is going to be deleted");

		    /* Delete student in db */
		    mapper.delete(student);
		  }
}
