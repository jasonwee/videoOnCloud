package play.learn.java.design.double_dispatch;

import java.util.ArrayList;
import java.util.List;

// https://java-design-patterns.com/patterns/double-dispatch/
public class DoubleDispatchDemo {
	public static void main(String[] args) {
		// initialize game objects and print their status
		List<GameObject> objects = new ArrayList<>();
		objects.add(new FlamingAsteroid(0, 0, 5, 5));
		objects.add(new SpaceStationMir(1, 1, 2, 2));
		objects.add(new Meteoroid(10, 10, 15, 15));
		objects.add(new SpaceStationIss(12, 12, 14, 14));
		objects.stream().forEach(o -> System.out.println(o.toString()));
		System.out.println("");

		// collision check
		objects.stream().forEach(o1 -> objects.stream().forEach(o2 -> {
			if (o1 != o2 && o1.intersectsWith(o2)) {
				o1.collision(o2);
			}
		}));
		System.out.println("");

		// output eventual object statuses
		objects.stream().forEach(o -> System.out.println(o.toString()));
		System.out.println("");
	}
}
