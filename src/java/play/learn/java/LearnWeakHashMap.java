package play.learn.java;

import java.util.Map;
import java.util.WeakHashMap;

public class LearnWeakHashMap {

	public static void main(String[] args) {
		Map<Object, Object> objectMap = new WeakHashMap<Object, Object>(1000);
		for (int i = 0; i < 1000; i++) {
			objectMap.put(String.valueOf(i), new Object());
			System.gc();
			System.out.println("Map size :" + objectMap.size());
		}
		
        objectMap = new WeakHashMap<Object, Object>();
		for (int i = 0; i < 1000; i++) {
			objectMap.put(i, new Object());
			System.gc();
			System.out.println("Map size :" + objectMap.size());
		}

	}

}
