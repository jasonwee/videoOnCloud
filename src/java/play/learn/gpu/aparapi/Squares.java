package play.learn.gpu.aparapi;

import com.amd.aparapi.Kernel;
import com.amd.aparapi.Range;

/**
 * how to run
 * java -Djava.library.path=../.. -Dcom.amd.aparapi.executionMode=%1 -classpath ../../aparapi.jar:squares.jar play.learn.gpu.aparapi.Squares
 *
 */
public class Squares {

	public static void main(String[] args) {
		final int size  = 512;
		
		final float[] values = new float[size];
		
		for (int i = 0; i < size; i++) {
			values[i] = i;
		}
		
		final float[] squares = new float[size];
		
		Kernel kernel = new Kernel() {

			@Override
			public void run() {
				int gid = getGlobalId();
				squares[gid] = values[gid] * values[gid];
			}
		};
		
		kernel.execute(Range.create(512));
		
		// report target execution mode: gpu or jtp ( java thread pool).
		System.out.println("Execution mode=" + kernel.getExecutionMode());
		
		// display computed square values.
		for (int i = 0; i < size; i++) {
			System.out.printf("%6.0f %8.0f\n", values[i], squares[i]);
		}
		
		kernel.dispose();

	}

}
