package play.learn.java.design.visitor;

public class SergeantVisitor implements UnitVisitor {

	  @Override
	  public void visitSoldier(Soldier soldier) {
	    // Do nothing
	  }

	  @Override
	  public void visitSergeant(Sergeant sergeant) {
	    System.out.printf("Hello %s", sergeant);
	  }

	  @Override
	  public void visitCommander(Commander commander) {
	    // Do nothing
	  }


}
