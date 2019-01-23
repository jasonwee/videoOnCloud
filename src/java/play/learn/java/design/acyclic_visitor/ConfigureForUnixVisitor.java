package play.learn.java.design.acyclic_visitor;

public class ConfigureForUnixVisitor implements ZoomVisitor {
	public void visit(Zoom zoom) {
		System.out.println(zoom + " used with Unix configurator.");
	}
}
