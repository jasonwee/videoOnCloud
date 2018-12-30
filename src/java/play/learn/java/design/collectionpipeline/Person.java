package play.learn.java.design.collectionpipeline;

import java.util.List;

public class Person {
	
	  private List<Car> cars;

	  /**
	   * Constructor to create an instance of person.
	   * @param cars the list of cars owned
	   */
	  public Person(List<Car> cars) {
	    this.cars = cars;
	  }

	  public List<Car> getCars() {
	    return cars;
	  }

}
