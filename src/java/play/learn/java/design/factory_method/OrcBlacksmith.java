package play.learn.java.design.factory_method;

public class OrcBlacksmith implements Blacksmith {

	@Override
	public Weapon manufactureWeapon(WeaponType weaponType) {
	    return new OrcWeapon(weaponType);
	}

}
