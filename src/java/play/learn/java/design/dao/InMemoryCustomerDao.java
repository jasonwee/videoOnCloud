package play.learn.java.design.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class InMemoryCustomerDao implements CustomerDao {


	  private Map<Integer, Customer> idToCustomer = new HashMap<>();

	  /**
	   * An eagerly evaluated stream of customers stored in memory.
	   */
	  @Override
	  public Stream<Customer> getAll() {
	    return idToCustomer.values().stream();
	  }

	  @Override
	  public Optional<Customer> getById(final int id) {
	    return Optional.ofNullable(idToCustomer.get(id));
	  }

	  @Override
	  public boolean add(final Customer customer) {
	    if (getById(customer.getId()).isPresent()) {
	      return false;
	    }
	    
	    idToCustomer.put(customer.getId(), customer);
	    return true;
	  }

	  @Override
	  public boolean update(final Customer customer) {
	    return idToCustomer.replace(customer.getId(), customer) != null;
	  }

	  @Override
	  public boolean delete(final Customer customer) {
	    return idToCustomer.remove(customer.getId()) != null;
	  }

}
