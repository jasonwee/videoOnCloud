package play.learn.java.design.specification;

public enum Movement {

	WALKING("walking"), SWIMMING("swimming"), FLYING("flying");

	private String title;

	Movement(String title) {
	    this.title = title;
	  }

	@Override
	  public String toString() {
	    return title;
	  }

}
