package play.learn.java.design.ambassador;

// https://java-design-patterns.com/patterns/ambassador/
public class AmbassadorDemo {

	public static void main(String[] args) {
		Client host1 = new Client();
		Client host2 = new Client();
		host1.useService(12);
		host2.useService(73);
	}

}
