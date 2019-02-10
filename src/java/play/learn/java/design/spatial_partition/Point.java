package play.learn.java.design.spatial_partition;

import java.util.ArrayList;
import java.util.Hashtable;

public abstract class Point<T> {

	public int x;
	public int y;
	public final int id;

	Point(int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	/**
	 * defines how the object moves
	 */
	abstract void move();

	/**
	 * defines conditions for interacting with an object obj
	 * 
	 * @param obj
	 *            is another object on field which also extends Point
	 * @return whether the object can interact with the other or not
	 */
	abstract boolean touches(T obj);

	/**
	 * handling interactions/collisions with other objects
	 * 
	 * @param pointsToCheck
	 *            contains the objects which need to be checked
	 * @param allPoints
	 *            contains hashtable of all points on field at this time
	 */
	abstract void handleCollision(ArrayList<Point> pointsToCheck, Hashtable<Integer, T> allPoints);

}
