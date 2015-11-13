package play.learn.java.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


/**
 *  parallel decomposition design
 *
 */
public class Solver {

	final int N;
	final float[][] data;
	final CyclicBarrier barrier;

	class Worker implements Runnable {
		int myRow;
		boolean done;

		Worker(int row) {
			myRow = row;
		}

		public void run() {
			while (!done()) {
				processRow(myRow);

				try {
					barrier.await();
				} catch (InterruptedException ex) {
					return;
				} catch (BrokenBarrierException ex) {
					return;
				}
			}
		}
		
		public boolean done() {
			return done;
		}
		
		private void processRow(int row) {
			System.out.println(Thread.currentThread().getName() + " processing row " + row );
			done = true;
		}
	}

	public Solver(float[][] matrix) {
	     data = matrix;
	     N = matrix.length;
	     Runnable barrierAction = new Runnable() { 
	    	 public void run() { 
	    		 //mergeRows(...); 
	    		 System.out.println("merging row");
	    	 }
	     };
	     barrier = new CyclicBarrier(N, barrierAction);

	     List<Thread> threads = new ArrayList<Thread>(N);
	     for (int i = 0; i < N; i++) {
	       Thread thread = new Thread(new Worker(i));
	       threads.add(thread);
	       thread.start();
	     }

	     // wait until done
	     for (Thread thread : threads)
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	   }
}
