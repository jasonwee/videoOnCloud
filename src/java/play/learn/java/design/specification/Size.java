package play.learn.java.design.specification;

public enum Size {
	
	  SMALL("small"), NORMAL("normal"), LARGE("large");

	  private String title;

	  Size(String title) {
	    this.title = title;
	  }

	  @Override
	  public String toString() {
	    return title;
	  }

}
