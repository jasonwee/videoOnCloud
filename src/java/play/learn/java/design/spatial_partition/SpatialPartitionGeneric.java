package play.learn.java.design.spatial_partition;

import java.util.Hashtable;

public abstract class SpatialPartitionGeneric<T> {
	Hashtable<Integer, T> playerPositions;
	QuadTree qTree;

	/**
	 * handles collisions for object obj using quadtree
	 * 
	 * @param obj
	 *            is the object for which collisions need to be checked
	 */
	abstract void handleCollisionsUsingQt(T obj);
}
