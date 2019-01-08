package play.learn.java.design.factory_kit;

import java.util.function.Supplier;

public interface Builder {
	  void add(WeaponType name, Supplier<Weapon> supplier);
}
