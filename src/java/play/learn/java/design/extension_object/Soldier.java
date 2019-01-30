package play.learn.java.design.extension_object;

public class Soldier implements SoldierExtension {
	private SoldierUnit unit;

	public Soldier(SoldierUnit soldierUnit) {
		this.unit = soldierUnit;
	}

	@Override
	public void soldierReady() {
		System.out.println("[Solider] " + unit.getName() + "  is ready!");
	}
}
