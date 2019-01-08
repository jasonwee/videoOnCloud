package play.learn.java.design.factory_kit;

// https://java-design-patterns.com/patterns/factory-kit/
public class FactoryKitDemo {

	  public static void main(String[] args) {
	    WeaponFactory factory = WeaponFactory.factory(builder -> {
	      builder.add(WeaponType.SWORD, Sword::new);
	      builder.add(WeaponType.AXE, Axe::new);
	      builder.add(WeaponType.SPEAR, Spear::new);
	      builder.add(WeaponType.BOW, Bow::new);
	    });
	    Weapon axe = factory.create(WeaponType.SPEAR);
	    System.out.println(axe.toString());
	  }

}
