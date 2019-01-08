package play.learn.java.design.factory_method;

public class ElfWeapon implements Weapon {

	  private WeaponType weaponType;

	  public ElfWeapon(WeaponType weaponType) {
	    this.weaponType = weaponType;
	  }

	  @Override
	  public String toString() {
	    return "Elven " + weaponType;
	  }

	  @Override
	  public WeaponType getWeaponType() {
	    return weaponType;
	  }

}
