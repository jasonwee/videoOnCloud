package play.learn.java.design.marker;

public class Guard implements Permission {

	  protected static void enter() {
	    System.out.println("You can enter");
	  }


}
