package play.learn.java.design.command;

import java.util.Deque;
import java.util.LinkedList;

public class Wizard {

	private Deque<Command> undoStack = new LinkedList<>();
	private Deque<Command> redoStack = new LinkedList<>();

	public Wizard() {
		// comment to ignore sonar issue: LEVEL critical
	}

	/**
	 * Cast spell
	 */
	public void castSpell(Command command, Target target) {
		System.out.printf("%s casts %s at %s", this, command, target);
		command.execute(target);
		undoStack.offerLast(command);
	}

	/**
	 * Undo last spell
	 */
	public void undoLastSpell() {
		if (!undoStack.isEmpty()) {
			Command previousSpell = undoStack.pollLast();
			redoStack.offerLast(previousSpell);
			System.out.printf("%s undoes %s", this, previousSpell);
			previousSpell.undo();
		}
	}

	/**
	 * Redo last spell
	 */
	public void redoLastSpell() {
		if (!redoStack.isEmpty()) {
			Command previousSpell = redoStack.pollLast();
			undoStack.offerLast(previousSpell);
			System.out.printf("%s redoes %s", this, previousSpell);
			previousSpell.redo();
		}
	}

	@Override
	public String toString() {
		return "Wizard";
	}
}
