package play.learn.java;

import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

// https://minddotout.wordpress.com/2013/05/11/java-8-concurrency-longadder/
public class LongAdderTest {
	
	static HashMap<Class, String> measurements;
	
	long oldSchool;
	CyclicBarrier barrier;
	Thread[] threads;
	int addCount;
	
	public static void main(String[] args) throws Exception
	{
		initMeasurements();
		LongAdderTest tester = new LongAdderTest();
		tester.run(3);
		
		initMeasurements();
		
		System.out.println("--- measurements start here");
		System.out.println("Cores: " + Runtime.getRuntime().availableProcessors());
		
		for (int j = 0; j < 100; j++) {
			System.err.print("\n" + j + "--> ");
			
			for (int i = 1; i <= 1; i++) {
				tester = new LongAdderTest();
				tester.run(i);
			}
			for (Class cls : measurements.keySet()) {
				addMeasurement(cls, "\n");
			}
			System.out.println();
			for (Class cls : measurements.keySet()) {
				System.out.println(cls.toString());
				System.out.println(measurements.get(cls) + "\n\n\n\n");
			}
			System.out.println("$$$");
		}
	}
	
	static void initMeasurements() {
		measurements = new HashMap();
		measurements.put(SyncThread.class, "");
		//measurements.put(LongAdderThread.class, "");
		//measurements.put(AtomicLongThread.class, "");
		//measurements.put(EmptyThread.class, "");
	}
	
	static void addMeasurement(Class cls, String measurement) {
		measurements.put(cls, measurements.get(cls) + measurement);
	}
	
	public void run(int threadCount) throws Exception {
		barrier = new CyclicBarrier(threadCount + 1);
		System.err.println(threadCount + ",");
		measureThreads(SyncThread.class, threadCount);
		//measureThreads(AtomicLongThread.class, threadCount);
		//measureThreads(LongAdderThread.class, threadCount);
		//measureThread(EmptyThread.class, threadCount);
	}
	
	private <T extends MeasureThread> void measureThreads(Class<T> cls, int threadCount) throws Exception {
		createAndStartThreads(cls, threadCount);
		long startTime = System.nanoTime();
		waitForBarrier();
		waitForBarrier();
		long endTime = System.nanoTime();
		join();
		double duration = (endTime - startTime) / 1000000.0 / threadCount;
		addMeasurement(cls, duration + ",");
	}
	
	private <T extends MeasureThread> void createAndStartThreads(Class<T> cls, int threadCount) throws Exception {
		threads = new Thread[threadCount];
		for (int i = 0; i < threadCount; i++) {
			MeasureThread thread = cls.newInstance();
			thread.controller = this;
			threads[i] = new Thread(thread);
			threads[i].start();
		}
	}
	
	protected void waitForBarrier() {
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void join() {
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	
	static abstract class MeasureThread implements Runnable {
		
		public LongAdderTest controller;
		
		@Override
		public void run() {
			controller.waitForBarrier();
			for (int i = 0; i < controller.addCount; i++) {
				runMethod();
			}
			controller.waitForBarrier();
		}
		
		public abstract void runMethod();
	}
	
	static class SyncThread extends MeasureThread {
		@Override
		public void runMethod() {
			synchronized (controller) {
				controller.oldSchool++;
			}
		}
	}

}
