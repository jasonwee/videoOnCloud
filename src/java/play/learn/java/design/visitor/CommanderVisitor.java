package play.learn.java.design.visitor;

public class CommanderVisitor implements UnitVisitor {

	@Override
	public void visitSoldier(Soldier soldier) {
		// Do nothing
	}

	@Override
	public void visitSergeant(Sergeant sergeant) {
		// Do nothing
	}

	@Override
	public void visitCommander(Commander commander) {
		System.out.printf("Good to see you %s", commander);
	}

}
