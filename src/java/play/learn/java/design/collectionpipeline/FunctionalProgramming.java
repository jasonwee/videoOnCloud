package play.learn.java.design.collectionpipeline;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;


public class FunctionalProgramming {
	
	  private FunctionalProgramming() {
	  }

	  /**
	   * Method to get models using for collection pipeline.
	   * 
	   * @param cars {@link List} of {@link Car} to be used for filtering
	   * @return {@link List} of {@link String} representing models built after year 2000
	   */
	  public static List<String> getModelsAfter2000(List<Car> cars) {
	    return cars.stream().filter(car -> car.getYear() > 2000)
	      .sorted(Comparator.comparing(Car::getYear))
	      .map(Car::getModel).collect(Collectors.toList());
	  }
	  
	  /**
	   * Method to group cars by category using groupingBy
	   * 
	   * @param cars {@link List} of {@link Car} to be used for grouping
	   * @return {@link Map} with category as key and cars belonging to that category as value
	   */
	  public static Map<Category, List<Car>> getGroupingOfCarsByCategory(List<Car> cars) {
	    return cars.stream().collect(Collectors.groupingBy(Car::getCategory));
	  }
	  
	  /**
	   * Method to get all Sedan cars belonging to a group of persons sorted by year of manufacture 
	   * 
	   * @param persons {@link List} of {@link Person} to be used
	   * @return {@link List} of {@link Car} to belonging to the group
	   */
	  public static List<Car> getSedanCarsOwnedSortedByDate(List<Person> persons) {
	    return persons.stream().map(Person::getCars).flatMap(List::stream)
	      .filter(car -> Category.SEDAN.equals(car.getCategory()))
	      .sorted(Comparator.comparing(Car::getYear)).collect(Collectors.toList());
	  }
	  

}
