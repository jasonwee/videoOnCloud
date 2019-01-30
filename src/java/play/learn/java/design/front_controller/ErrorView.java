package play.learn.java.design.front_controller;

public class ErrorView implements View {

	@Override
	public void display() {
		System.out.println("Error 500");
	}
}
