package play.learn.java.design.extension_object;

public class Commander implements CommanderExtension {

	private CommanderUnit unit;

	public Commander(CommanderUnit commanderUnit) {
		this.unit = commanderUnit;
	}

	@Override
	public void commanderReady() {
		System.out.println("[Commander] " + unit.getName() + " is ready!");
	}

}
