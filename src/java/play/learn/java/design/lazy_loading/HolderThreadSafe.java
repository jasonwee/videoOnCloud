package play.learn.java.design.lazy_loading;

public class HolderThreadSafe {
	

	  private Heavy heavy;

	  /**
	   * Constructor
	   */
	  public HolderThreadSafe() {
	    System.out.println("HolderThreadSafe created");
	  }

	  /**
	   * Get heavy object
	   */
	  public synchronized Heavy getHeavy() {
	    if (heavy == null) {
	      heavy = new Heavy();
	    }
	    return heavy;
	  }

}
