package play.learn.java.design.front_controller;

// https://java-design-patterns.com/patterns/front-controller/
public class FrontControllerDemo {
	public static void main(String[] args) {
		FrontController controller = new FrontController();
		controller.handleRequest("Archer");
		controller.handleRequest("Catapult");
		controller.handleRequest("foobar");
	}
}
