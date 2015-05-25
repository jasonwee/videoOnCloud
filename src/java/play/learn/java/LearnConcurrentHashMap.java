package play.learn.java;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LearnConcurrentHashMap {

	// java8 https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentHashMap.html#keySet--
	// java7 http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ConcurrentHashMap.html
	// they return different things, watch out.
	public static void main(String[] args) {
		ConcurrentHashMap<String, String> properties = new ConcurrentHashMap<String, String>();
		properties.put("key", "value");
		properties.put("key", "value1");
		properties.put("key1", "value1");
		Set<String> keySet = properties.keySet();
		System.out.println(keySet);
	}

}
