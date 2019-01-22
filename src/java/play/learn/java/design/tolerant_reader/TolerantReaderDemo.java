package play.learn.java.design.tolerant_reader;

import java.io.IOException;

// https://java-design-patterns.com/patterns/tolerant-reader/
public class TolerantReaderDemo {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// Write V1
		RainbowFish fishV1 = new RainbowFish("Zed", 10, 11, 12);
		System.out.printf("fishV1 name=%s age=%s length=%s weight=%s", fishV1.getName(), fishV1.getAge(),
				fishV1.getLengthMeters(), fishV1.getWeightTons());
		RainbowFishSerializer.writeV1(fishV1, "fish1.out");
		// Read V1
		RainbowFish deserializedFishV1 = RainbowFishSerializer.readV1("fish1.out");
		System.out.printf("deserializedFishV1 name=%s age=%s length=%s weight=%s", deserializedFishV1.getName(),
				deserializedFishV1.getAge(), deserializedFishV1.getLengthMeters(), deserializedFishV1.getWeightTons());
		// Write V2
		RainbowFishV2 fishV2 = new RainbowFishV2("Scar", 5, 12, 15, true, true, true);
		System.out.printf("fishV2 name=%s age=%s length=%s weight=%s sleeping=%s hungry=%s angry=%s", fishV2.getName(),
				fishV2.getAge(), fishV2.getLengthMeters(), fishV2.getWeightTons(), fishV2.getHungry(),
				fishV2.getAngry(), fishV2.getSleeping());
		RainbowFishSerializer.writeV2(fishV2, "fish2.out");
		// Read V2 with V1 method
		RainbowFish deserializedFishV2 = RainbowFishSerializer.readV1("fish2.out");
		System.out.printf("deserializedFishV2 name=%s age=%s length=%s weight=%s", deserializedFishV2.getName(),
				deserializedFishV2.getAge(), deserializedFishV2.getLengthMeters(), deserializedFishV2.getWeightTons());
	}
}
