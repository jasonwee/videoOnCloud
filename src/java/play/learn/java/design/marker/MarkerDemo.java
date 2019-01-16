package play.learn.java.design.marker;

// https://java-design-patterns.com/patterns/marker/
public class MarkerDemo {

	public static void main(String[] args) {

		Guard guard = new Guard();
		Thief thief = new Thief();

		if (guard instanceof Permission) {
			guard.enter();
		} else {
			System.out.println("You have no permission to enter, please leave this area");
		}

		if (thief instanceof Permission) {
			thief.steal();
		} else {
			thief.doNothing();
		}
	}

}
