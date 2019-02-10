package play.learn.java.design.spatial_partition;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

// https://java-design-patterns.com/patterns/spatial-partition/
public class SpatialPartitionDemo {

	static void noSpatialPartition(int height, int width, int numOfMovements, Hashtable<Integer, Bubble> bubbles) {
		ArrayList<Point> bubblesToCheck = new ArrayList<Point>();
		for (Enumeration<Integer> e = bubbles.keys(); e.hasMoreElements();) {
			bubblesToCheck.add(bubbles.get(e.nextElement())); // all bubbles have to be checked for collision for all
																// bubbles
		}

		// will run numOfMovement times or till all bubbles have popped
		while (numOfMovements > 0 && !bubbles.isEmpty()) {
			for (Enumeration<Integer> e = bubbles.keys(); e.hasMoreElements();) {
				Integer i = e.nextElement();
				// bubble moves, new position gets updated, collisions checked with all bubbles
				// in bubblesToCheck
				bubbles.get(i).move();
				bubbles.replace(i, bubbles.get(i));
				bubbles.get(i).handleCollision(bubblesToCheck, bubbles);
			}
			numOfMovements--;
		}
		for (Integer key : bubbles.keySet()) {
			// bubbles not popped
			System.out.println("Bubble " + key + " not popped");
		}
	}

	static void withSpatialPartition(int height, int width, int numOfMovements, Hashtable<Integer, Bubble> bubbles) {
		// creating quadtree
		Rect rect = new Rect(width / 2, height / 2, width, height);
		QuadTree qTree = new QuadTree(rect, 4);

		// will run numOfMovement times or till all bubbles have popped
		while (numOfMovements > 0 && !bubbles.isEmpty()) {
			// quadtree updated each time
			for (Enumeration<Integer> e = bubbles.keys(); e.hasMoreElements();) {
				qTree.insert(bubbles.get(e.nextElement()));
			}
			for (Enumeration<Integer> e = bubbles.keys(); e.hasMoreElements();) {
				Integer i = e.nextElement();
				// bubble moves, new position gets updated, quadtree used to reduce computations
				bubbles.get(i).move();
				bubbles.replace(i, bubbles.get(i));
				SpatialPartitionBubbles sp = new SpatialPartitionBubbles(bubbles, qTree);
				sp.handleCollisionsUsingQt(bubbles.get(i));
			}
			numOfMovements--;
		}
		for (Integer key : bubbles.keySet()) {
			// bubbles not popped
			System.out.println("Bubble " + key + " not popped");
		}
	}

	/**
	 * Program entry point.
	 *
	 * @param args
	 *            command line args
	 */

	public static void main(String[] args) {
		Hashtable<Integer, Bubble> bubbles1 = new Hashtable<Integer, Bubble>();
		Hashtable<Integer, Bubble> bubbles2 = new Hashtable<Integer, Bubble>();
		Random rand = new Random();
		for (int i = 0; i < 10000; i++) {
			Bubble b = new Bubble(rand.nextInt(300), rand.nextInt(300), i, rand.nextInt(2) + 1);
			bubbles1.put(i, b);
			bubbles2.put(i, b);
			System.out.println("Bubble " + i + " with radius " + b.radius + " added at (" + b.x + "," + b.y + ")");
		}

		long start1 = System.currentTimeMillis();
		SpatialPartitionDemo.noSpatialPartition(300, 300, 20, bubbles1);
		long end1 = System.currentTimeMillis();
		long start2 = System.currentTimeMillis();
		SpatialPartitionDemo.withSpatialPartition(300, 300, 20, bubbles2);
		long end2 = System.currentTimeMillis();
		System.out.println("Without spatial partition takes " + (end1 - start1) + "ms");
		System.out.println("With spatial partition takes " + (end2 - start2) + "ms");
	}

}
