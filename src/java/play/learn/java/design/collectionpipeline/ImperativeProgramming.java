package play.learn.java.design.collectionpipeline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImperativeProgramming {

	private ImperativeProgramming() {

	}

	public static List<String> getModelsAfter2000(List<Car> cars) {
		List<Car> carsSortedByYear = new ArrayList<>();

		for (Car car : cars) {
			if (car.getYear() > 2000) {
				carsSortedByYear.add(car);
			}
		}

		Collections.sort(carsSortedByYear, new Comparator<Car>() {
			public int compare(Car car1, Car car2) {
				return new Integer(car1.getYear()).compareTo(car2.getYear());
			}
		});

		List<String> models = new ArrayList<>();
		for (Car car : carsSortedByYear) {
			models.add(car.getModel());
		}

		return models;
	}
	
	  /**
	   * Method to group cars by category using for loops
	   * 
	   * @param cars {@link List} of {@link Car} to be used for grouping
	   * @return {@link Map} with category as key and cars belonging to that category as value
	   */
	  public static Map<Category, List<Car>> getGroupingOfCarsByCategory(List<Car> cars) {
	    Map<Category, List<Car>> groupingByCategory = new HashMap<>();
	    for (Car car: cars) {
	      if (groupingByCategory.containsKey(car.getCategory())) {
	        groupingByCategory.get(car.getCategory()).add(car);
	      } else {
	        List<Car> categoryCars = new ArrayList<>();
	        categoryCars.add(car);
	        groupingByCategory.put(car.getCategory(), categoryCars);
	      }
	    }
	    return groupingByCategory;
	  }
	
	  /**
	   * Method to get all Sedan cars belonging to a group of persons sorted by year of manufacture using for loops 
	   * 
	   * @param persons {@link List} of {@link Person} to be used
	   * @return {@link List} of {@link Car} to belonging to the group
	   */
	  public static List<Car> getSedanCarsOwnedSortedByDate(List<Person> persons) {
	    List<Car> cars = new ArrayList<>();
	    for (Person person: persons) {
	      cars.addAll(person.getCars());
	    }
	  
	    List<Car> sedanCars = new ArrayList<>();
	    for (Car car: cars) {
	      if (Category.SEDAN.equals(car.getCategory())) {
	        sedanCars.add(car);
	      }
	    }
	  
	    sedanCars.sort(new Comparator<Car>() {
	      @Override
	      public int compare(Car o1, Car o2) {
	        return o1.getYear() - o2.getYear();
	      }
	    });
	  
	    return sedanCars;
	  }

}
