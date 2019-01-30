package play.learn.java.design.extension_object;

public class SoldierUnit extends Unit {
	public SoldierUnit(String name) {
		super(name);
	}

	@Override
	public UnitExtension getUnitExtension(String extensionName) {

		if (extensionName.equals("SoldierExtension")) {
			if (unitExtension == null) {
				unitExtension = new Soldier(this);
			}

			return unitExtension;
		}
		return super.getUnitExtension(extensionName);
	}
}
