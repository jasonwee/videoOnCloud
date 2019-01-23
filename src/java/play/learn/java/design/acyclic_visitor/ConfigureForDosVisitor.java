package play.learn.java.design.acyclic_visitor;

public class ConfigureForDosVisitor implements AllModemVisitor {

	public void visit(Hayes hayes) {
		System.out.println(hayes + " used with Dos configurator.");
	}

	public void visit(Zoom zoom) {
		System.out.println(zoom + " used with Dos configurator.");
	}
}
