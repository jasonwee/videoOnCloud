package play.learn.java.design.layers;

import java.util.Optional;


public class CakeLayerInfo {
	public final Optional<Long> id;
	public final String name;
	public final int calories;

	/**
	 * Constructor
	 */
	public CakeLayerInfo(Long id, String name, int calories) {
		this.id = Optional.of(id);
		this.name = name;
		this.calories = calories;
	}

	/**
	 * Constructor
	 */
	public CakeLayerInfo(String name, int calories) {
		this.id = Optional.empty();
		this.name = name;
		this.calories = calories;
	}

	@Override
	public String toString() {
		return String.format("CakeLayerInfo id=%d name=%s calories=%d", id.orElse(-1L), name, calories);
	}
}
