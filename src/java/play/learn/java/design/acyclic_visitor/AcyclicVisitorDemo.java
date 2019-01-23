package play.learn.java.design.acyclic_visitor;

// https://java-design-patterns.com/patterns/acyclic-visitor/
public class AcyclicVisitorDemo {

	public static void main(String[] args) {
		ConfigureForUnixVisitor conUnix = new ConfigureForUnixVisitor();
		ConfigureForDosVisitor conDos = new ConfigureForDosVisitor();

		Zoom zoom = new Zoom();
		Hayes hayes = new Hayes();

		hayes.accept(conDos); // Hayes modem with Unix configurator
		zoom.accept(conDos); // Zoom modem with Dos configurator
		hayes.accept(conUnix); // Hayes modem with Unix configurator
		zoom.accept(conUnix); // Zoom modem with Unix configurator
	}

}
