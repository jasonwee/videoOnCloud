package play.learn.java.design.dao;

import java.util.Optional;
import java.util.stream.Stream;

public interface CustomerDao {
	
	  /**
	   * @return all the customers as a stream. The stream may be lazily or eagerly evaluated based 
	   *     on the implementation. The stream must be closed after use.
	   * @throws Exception if any error occurs.
	   */
	  Stream<Customer> getAll() throws Exception;
	  
	  /**
	   * @param id unique identifier of the customer.
	   * @return an optional with customer if a customer with unique identifier <code>id</code>
	   *     exists, empty optional otherwise.
	   * @throws Exception if any error occurs.
	   */
	  Optional<Customer> getById(int id) throws Exception;

	  /**
	   * @param customer the customer to be added.
	   * @return true if customer is successfully added, false if customer already exists.
	   * @throws Exception if any error occurs.
	   */
	  boolean add(Customer customer) throws Exception;

	  /**
	   * @param customer the customer to be updated.
	   * @return true if customer exists and is successfully updated, false otherwise.
	   * @throws Exception if any error occurs.
	   */
	  boolean update(Customer customer) throws Exception;

	  /**
	   * @param customer the customer to be deleted.
	   * @return true if customer exists and is successfully deleted, false otherwise.
	   * @throws Exception if any error occurs.
	   */
	  boolean delete(Customer customer) throws Exception;

}
