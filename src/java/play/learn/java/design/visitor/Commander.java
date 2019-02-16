package play.learn.java.design.visitor;

public class Commander extends Unit {

	public Commander(Unit... children) {
		super(children);
	}

	@Override
	public void accept(UnitVisitor visitor) {
		visitor.visitCommander(this);
		super.accept(visitor);
	}

	@Override
	public String toString() {
		return "commander";
	}

}
