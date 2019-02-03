package play.learn.java.design.mvc;

// https://java-design-patterns.com/patterns/model-view-controller/
public class MVCDemo {
	public static void main(String[] args) {
		// create model, view and controller
		GiantModel giant = new GiantModel(Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED);
		GiantView view = new GiantView();
		GiantController controller = new GiantController(giant, view);
		// initial display
		controller.updateView();
		// controller receives some interactions that affect the giant
		controller.setHealth(Health.WOUNDED);
		controller.setNourishment(Nourishment.HUNGRY);
		controller.setFatigue(Fatigue.TIRED);
		// redisplay
		controller.updateView();
	}

}
