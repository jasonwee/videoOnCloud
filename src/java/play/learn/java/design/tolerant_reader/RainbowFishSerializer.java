package play.learn.java.design.tolerant_reader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public final class RainbowFishSerializer {
	private RainbowFishSerializer() {
	}

	/**
	 * Write V1 RainbowFish to file
	 */
	public static void writeV1(RainbowFish rainbowFish, String filename) throws IOException {
		Map<String, String> map = new HashMap<>();
		map.put("name", rainbowFish.getName());
		map.put("age", String.format("%d", rainbowFish.getAge()));
		map.put("lengthMeters", String.format("%d", rainbowFish.getLengthMeters()));
		map.put("weightTons", String.format("%d", rainbowFish.getWeightTons()));
		try (FileOutputStream fileOut = new FileOutputStream(filename);
				ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {
			objOut.writeObject(map);
		}
	}

	/**
	 * Write V2 RainbowFish to file
	 */
	public static void writeV2(RainbowFishV2 rainbowFish, String filename) throws IOException {
		Map<String, String> map = new HashMap<>();
		map.put("name", rainbowFish.getName());
		map.put("age", String.format("%d", rainbowFish.getAge()));
		map.put("lengthMeters", String.format("%d", rainbowFish.getLengthMeters()));
		map.put("weightTons", String.format("%d", rainbowFish.getWeightTons()));
		map.put("angry", Boolean.toString(rainbowFish.getAngry()));
		map.put("hungry", Boolean.toString(rainbowFish.getHungry()));
		map.put("sleeping", Boolean.toString(rainbowFish.getSleeping()));
		try (FileOutputStream fileOut = new FileOutputStream(filename);
				ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {
			objOut.writeObject(map);
		}
	}

	/**
	 * Read V1 RainbowFish from file
	 */
	public static RainbowFish readV1(String filename) throws IOException, ClassNotFoundException {
		Map<String, String> map = null;

		try (FileInputStream fileIn = new FileInputStream(filename);
				ObjectInputStream objIn = new ObjectInputStream(fileIn)) {
			map = (Map<String, String>) objIn.readObject();
		}

		return new RainbowFish(map.get("name"), Integer.parseInt(map.get("age")),
				Integer.parseInt(map.get("lengthMeters")), Integer.parseInt(map.get("weightTons")));
	}
}
