package play.learn.java.concurrent;

import java.util.concurrent.CountedCompleter;

public class MapReducer<E> extends CountedCompleter<E> {
	
	final E[] array;
	final MyMapper<E> mapper;
	
	final MyReducer<E> reducer;
	final int lo,hi;
	
	MapReducer<E> sibling;
	E result;
	
	MapReducer(CountedCompleter<?> p, E[] array, MyMapper<E> mapper, MyReducer<E> reducer, int lo, int hi) {
		super(p);
		this.array = array;
		this.mapper = mapper;
		this.reducer = reducer;
		this.lo = lo;
		this.hi = hi;
	}

	/**
	 *	int A = 10;
	 *	//initial 2 is 1010;
	 *	// A >>> 2
	 *	// 1010 -> 001010
	 *	// 0010 (to retain only 4 bit as match with initial.
	 *	// so 0010 is 2;
	 *	System.out.println(A >>> 2);
	 *	2
	 */
	@Override
	public void compute() {
		if (hi - lo >= 2) {
			// unsigned right shift
			int mid = (lo + hi) >>> 1;
		    MapReducer<E> left = new MapReducer(this, array, mapper, reducer, lo, mid);
		    MapReducer<E> right = new MapReducer(this, array, mapper, reducer, mid, hi);
		    left.sibling = right;
		    right.sibling = left;
		    setPendingCount(1); // only right is pending
		    right.fork();
		    left.compute();     // directly execute left
		} else {
			if (hi > lo)
				result = mapper.apply(array[lo]);
			tryComplete();
		}
	}

	@Override
	public void onCompletion(CountedCompleter<?> caller) {
		if (caller != this) {
			MapReducer<E> child = (MapReducer<E>) caller;
			MapReducer<E> sib = child.sibling;
			
			if (sib == null || sib.result == null)
				result = child.result;
			else
				result = reducer.apply(child.result, sib.result);
		}
	}
	
	public E getRawResult() {
		return result;
	}
	
	public static <E> E mapReduce(E[] array, MyMapper<E> mapper, MyReducer<E> reducer) {
		return new MapReducer<E>(null, array, mapper, reducer, 0, array.length).invoke();
	}

}
