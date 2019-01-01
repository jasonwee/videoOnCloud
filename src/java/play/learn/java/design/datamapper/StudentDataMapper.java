package play.learn.java.design.datamapper;

import java.util.Optional;

public interface StudentDataMapper {
	
	  Optional<Student> find(int studentId);

	  void insert(Student student) throws DataMapperException;

	  void update(Student student) throws DataMapperException;

	  void delete(Student student) throws DataMapperException;

}
