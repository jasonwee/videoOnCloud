package play.learn.gpu.aparapi;

import com.amd.aparapi.Kernel;
import com.amd.aparapi.Range;

/**
 * to run 
 * $ optirun java  -Djava.library.path=../.. -Dcom.amd.aparapi.executionMode=%1 -classpath ../../aparapi.jar:add.jar play.learn.gpu.aparapi.Add
 *
 */
public class Add {

	public static void main(String[] args) {
		
		final int size = 512;
		
		final float[] a = new float[size];
		final float[] b = new float[size];
		
		for (int i =0; i < size; i++) {
			a[i] = (float)(Math.random() * 100);
			b[i] = (float)(Math.random() * 100);
		}
		
		final float[] sum = new float[size];
		
		Kernel kernel = new Kernel() {
			@Override
			public void run() {
				int gid = getGlobalId();
				sum[gid] = a[gid] + b[gid];
			}
		};
		
		kernel.execute(Range.create(512));
		
		for (int i = 0; i < size; i++) {
			System.out.printf("%6.2f + %6.2f = %8.2f\n", a[i], b[i], sum[i]);
		}
		
		kernel.dispose();
	}

}
