package play.learn.java.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class LearnBrokenBarrierException {
	
	private CyclicBarrier cibai;
	public static int count = 0;
	
	private void manageThread() {
		cibai = new CyclicBarrier(3);
		
		for (int i = 0; i < 3; i++) {
			new Thread(new Worker(cibai)).start();
		}
	}
	
	public static void barrierComplete(CyclicBarrier cb) {
		System.out.println("collating task");
		
		if (count == 3) {
			System.out.println("Exit from system");
			// comment for finite
			System.exit(0);
		}
		count++;
		
		for (int i = 0; i < 3; i++) {
            new Thread(new Worker(cb)).start();
		}
	}
	
	public static void main(String[] args) {
		new LearnBrokenBarrierException().manageThread(); 
	}
	
	static class Worker implements Runnable {
		
		CyclicBarrier cibai;
		
		public Worker(CyclicBarrier cb) {
			this.cibai = cb;
		}
		
		@Override
		public void run() {
			doSomeWork();
			try {
				if (cibai.await() == 0)
					LearnBrokenBarrierException.barrierComplete(cibai);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}

		private void doSomeWork() {
			System.out.println("Doing some work");
		}
		
	}

}
