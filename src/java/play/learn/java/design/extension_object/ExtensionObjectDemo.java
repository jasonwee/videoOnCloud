package play.learn.java.design.extension_object;

// https://java-design-patterns.com/patterns/extension-objects/
public class ExtensionObjectDemo {
	public static void main(String[] args) {

		// Create 3 different units
		Unit soldierUnit = new SoldierUnit("SoldierUnit1");
		Unit sergeantUnit = new SergeantUnit("SergeantUnit1");
		Unit commanderUnit = new CommanderUnit("CommanderUnit1");

		// check for each unit to have an extension
		checkExtensionsForUnit(soldierUnit);
		checkExtensionsForUnit(sergeantUnit);
		checkExtensionsForUnit(commanderUnit);

	}

	private static void checkExtensionsForUnit(Unit unit) {

		SoldierExtension soldierExtension = (SoldierExtension) unit.getUnitExtension("SoldierExtension");
		SergeantExtension sergeantExtension = (SergeantExtension) unit.getUnitExtension("SergeantExtension");
		CommanderExtension commanderExtension = (CommanderExtension) unit.getUnitExtension("CommanderExtension");

		// if unit have extension call the method
		if (soldierExtension != null) {
			soldierExtension.soldierReady();
		} else {
			System.out.println(unit.getName() + " without SoldierExtension");
		}

		if (sergeantExtension != null) {
			sergeantExtension.sergeantReady();
		} else {
			System.out.println(unit.getName() + " without SergeantExtension");
		}

		if (commanderExtension != null) {
			commanderExtension.commanderReady();
		} else {
			System.out.println(unit.getName() + " without CommanderExtension");
		}
	}
}
