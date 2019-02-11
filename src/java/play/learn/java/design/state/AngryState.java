package play.learn.java.design.state;

public class AngryState implements State {

	  private Mammoth mammoth;

	  public AngryState(Mammoth mammoth) {
	    this.mammoth = mammoth;
	  }

	  @Override
	  public void observe() {
	    System.out.printf("%s is furious!", mammoth);
	  }

	  @Override
	  public void onEnterState() {
		  System.out.printf("%s gets angry!", mammoth);
	  }

}
