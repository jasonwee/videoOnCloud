package play.learn.java.design.visitor;

// https://java-design-patterns.com/patterns/visitor/
public class VisitorDemo {
	public static void main(String[] args) {
		Commander commander = new Commander(new Sergeant(new Soldier(), new Soldier(), new Soldier()),
				new Sergeant(new Soldier(), new Soldier(), new Soldier()));
		commander.accept(new SoldierVisitor());
		commander.accept(new SergeantVisitor());
		commander.accept(new CommanderVisitor());
	}
}
