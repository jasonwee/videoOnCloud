package play.learn.java.design.front_controller;

public class UnknownCommand implements Command {
	@Override
	public void process() {
		new ErrorView().display();
	}
}
