package play.learn.java.design.extension_object;

public class Unit {
	private String name;
	protected UnitExtension unitExtension = null;

	public Unit(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UnitExtension getUnitExtension(String extensionName) {
		return null;
	}
}
