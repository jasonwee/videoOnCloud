package play.learn.java.design.command;

// https://java-design-patterns.com/patterns/command/
public class CommandDemo {

	public static void main(String[] args) {
		Wizard wizard = new Wizard();
		Goblin goblin = new Goblin();

		goblin.printStatus();

		wizard.castSpell(new ShrinkSpell(), goblin);
		goblin.printStatus();

		wizard.castSpell(new InvisibilitySpell(), goblin);
		goblin.printStatus();

		wizard.undoLastSpell();
		goblin.printStatus();

		wizard.undoLastSpell();
		goblin.printStatus();

		wizard.redoLastSpell();
		goblin.printStatus();

		wizard.redoLastSpell();
		goblin.printStatus();
	}

}
