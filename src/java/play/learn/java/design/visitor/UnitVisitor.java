package play.learn.java.design.visitor;

public interface UnitVisitor {
	void visitSoldier(Soldier soldier);

	void visitSergeant(Sergeant sergeant);

	void visitCommander(Commander commander);
}
