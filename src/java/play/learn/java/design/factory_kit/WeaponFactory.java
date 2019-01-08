package play.learn.java.design.factory_kit;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface WeaponFactory {
	
	  /**
	   * Creates an instance of the given type.
	   * @param name representing enum of an object type to be created.
	   * @return new instance of a requested class implementing {@link Weapon} interface.
	   */
	  Weapon create(WeaponType name);

	  /**
	   * Creates factory - placeholder for specified {@link Builder}s.
	   * @param consumer for the new builder to the factory.
	   * @return factory with specified {@link Builder}s
	   */
	  static WeaponFactory factory(Consumer<Builder> consumer) {
	    Map<WeaponType, Supplier<Weapon>> map = new HashMap<>();
	    consumer.accept(map::put);
	    return name -> map.get(name).get();
	  }

}
