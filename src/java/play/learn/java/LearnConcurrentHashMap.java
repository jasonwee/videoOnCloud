package play.learn.java;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.util.concurrent.atomic.DoubleAccumulator;

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
		//System.out.println(keySet);
		
		ConcurrentHashMap<String, Integer> properties1 = new ConcurrentHashMap<String, Integer>();
		properties1.put("1", 1);
		properties1.put("2", 2);
		properties1.put("3", 3);
		properties1.put("4", 4);
		properties1.put("5", 5);
		//properties1.forEachKey(2, (it) -> System.out.println(it));
		
		//properties1.forEachValue(2, (it) -> System.out.println(it));
		
		//properties1.forEachEntry(2, (it) -> System.out.println(it.getValue() + 1));
		
		properties1.search(1, (x,y) -> {return x + y;});
		
		properties1.searchKeys(10, (x) -> { return x; } ) ;
		
		properties1.searchValues(10, (x) -> { return x; } )  ;
		
		properties1.searchEntries(3, (x) -> { return x; } ) ;
		
		properties1.reduce(1, (x,y) -> {return x+y;}, (x,y) -> { return x + y; });
		
		properties1.mappingCount();
		
		KeySetView<Object, Boolean> ksView = ConcurrentHashMap.newKeySet();
		
	}

}
