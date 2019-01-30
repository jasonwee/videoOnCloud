package play.learn.java.design.front_controller;

public class ArcherCommand implements Command {
	 @Override
	  public void process() {
	    new ArcherView().display();
	  }
}
