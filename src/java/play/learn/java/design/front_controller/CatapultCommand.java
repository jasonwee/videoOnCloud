package play.learn.java.design.front_controller;

public class CatapultCommand implements Command {
	@Override
	public void process() {
		new CatapultView().display();
	}
}
