package play.learn.java.design.factory_method;

// https://java-design-patterns.com/patterns/factory-method/
public class FactoryMethodDemo {

	private final Blacksmith blacksmith;

	/**
	   * Creates an instance of <code>FactoryMethodDemo</code> which will use <code>blacksmith</code> to manufacture 
	   * the weapons for war.
	   * <code>FactoryMethodDemo</code> is unaware which concrete implementation of {@link Blacksmith} it is using.
	   * The decision of which blacksmith implementation to use may depend on configuration, or
	   * the type of rival in war.
	   * @param blacksmith a non-null implementation of blacksmith
	   */
	  public FactoryMethodDemo(Blacksmith blacksmith) {
	    this.blacksmith = blacksmith;
	  }

	/**
	 * Program entry point
	 * 
	 * @param args
	 *            command line args
	 */
	public static void main(String[] args) {
		// Lets go to war with Orc weapons
		FactoryMethodDemo app = new FactoryMethodDemo(new OrcBlacksmith());
		app.manufactureWeapons();

		// Lets go to war with Elf weapons
		app = new FactoryMethodDemo(new ElfBlacksmith());
		app.manufactureWeapons();
	}

	private void manufactureWeapons() {
		Weapon weapon;
		weapon = blacksmith.manufactureWeapon(WeaponType.SPEAR);
		System.out.println(weapon.toString());
		weapon = blacksmith.manufactureWeapon(WeaponType.AXE);
		System.out.println(weapon.toString());
	}

}
