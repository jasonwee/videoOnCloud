package play.learn.java.design.service_layer;

import java.util.List;


public interface Dao<E extends BaseEntity> {
	E find(Long id);

	void persist(E entity);

	E merge(E entity);

	void delete(E entity);

	List<E> findAll();
}
