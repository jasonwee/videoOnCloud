package play.learn.java.concurrent;

import java.util.concurrent.Callable;


/**
 * Callable: 
 * - A task that returns a result and may throw an exception.
 *  
 * @author jason
 *
 */
public class Summer implements Callable<Integer> {
	
	private int a;
	private int b;
	
	public Summer(int a, int b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public Integer call() throws Exception {
		return a+b;
	}

}
