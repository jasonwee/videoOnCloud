package play.learn.java.design.extension_object;

public class CommanderUnit extends Unit {
	public CommanderUnit(String name) {
		super(name);
	}

	@Override
	public UnitExtension getUnitExtension(String extensionName) {

		if (extensionName.equals("CommanderExtension")) {
			if (unitExtension == null) {
				unitExtension = new Commander(this);
			}
			return unitExtension;
		}

		return super.getUnitExtension(extensionName);
	}
}
