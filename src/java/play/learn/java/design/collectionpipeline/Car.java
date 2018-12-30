package play.learn.java.design.collectionpipeline;

public class Car {
	  private final String make;
	  private final String model;
	  private final int year;
	  private final Category category;

	  /**
	   * Constructor to create an instance of car.
	   * @param make the make of the car
	   * @param model the model of the car
	   * @param yearOfMake the year of built of the car
	   * @param category the {@link Category} of the car
	   */
	  public Car(String make, String model, int yearOfMake, Category category) {
	    this.make = make;
	    this.model = model;
	    this.year = yearOfMake;
	    this.category = category;
	  }

	  @Override
	  public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((category == null) ? 0 : category.hashCode());
	    result = prime * result + ((make == null) ? 0 : make.hashCode());
	    result = prime * result + ((model == null) ? 0 : model.hashCode());
	    result = prime * result + year;
	    return result;
	  }

	  @Override
	  public boolean equals(Object obj) {
	    if (this == obj) {
	      return true;
	    }
	    if (obj == null) {
	      return false;
	    }
	    if (getClass() != obj.getClass()) {
	      return false;
	    }
	    Car other = (Car) obj;
	    if (category != other.category) {
	      return false;
	    }
	    if (make == null) {
	      if (other.make != null) {
	        return false;
	      }
	    } else if (!make.equals(other.make)) {
	      return false;
	    }
	    if (model == null) {
	      if (other.model != null) {
	        return false;
	      }
	    } else if (!model.equals(other.model)) {
	      return false;
	    }
	    if (year != other.year) {
	      return false;
	    }
	    return true;
	  }

	  public String getMake() {
	    return make;
	  }

	  public String getModel() {
	    return model;
	  }

	  public int getYear() {
	    return year;
	  }
	  
	  public Category getCategory() {
	    return category;
	  }
}