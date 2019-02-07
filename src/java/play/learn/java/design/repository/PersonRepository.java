package play.learn.java.design.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long>, JpaSpecificationExecutor<Person> {
	Person findByName(String name);
}
