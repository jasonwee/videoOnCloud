package play.learn.java.design.extension_object;

public class SergeantUnit extends Unit {
	public SergeantUnit(String name) {
		super(name);
	}

	@Override
	public UnitExtension getUnitExtension(String extensionName) {

		if (extensionName.equals("SergeantExtension")) {
			if (unitExtension == null) {
				unitExtension = new Sergeant(this);
			}
			return unitExtension;
		}

		return super.getUnitExtension(extensionName);
	}
}
