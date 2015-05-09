package play.learn.java.stamplock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 *  // http://blog.takipi.com/java-8-stampedlocks-vs-readwritelocks-and-synchronized/
 *  
 *  adapted from the blog above, but modify and so we focus on only 
 *  <code>StampLock</code>
 *  
 * @author jason
 *
 */
public class LearnStampLock {
		
	public static int ROUNDS 			= 1;
	public static int THREADS 			= 10;
	public static long TARGET_NUMBER 	= 100000000l;
	
	private static long start;
	private static Boolean[] rounds;
	
	private static String COUNTER 		= Counters.STAMPED.toString();
	private static int round;
	
	private static ExecutorService executor;
	
	private static enum Counters {
		STAMPED
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		rounds = new Boolean[ROUNDS];
		
		System.out.println("Using " + COUNTER + ". threads: " + THREADS + ". rounds: " + ROUNDS +
				". Target: " + TARGET_NUMBER);
		
		for (round = 0; round < ROUNDS; round++)
		{
			rounds[round] = Boolean.FALSE;
			
			Counter counter = new Stamped();
			
			executor = Executors.newFixedThreadPool(THREADS);
			
			start = System.currentTimeMillis();
			
			for (int j = 0; j < THREADS; j+=2)
			{	
				executor.execute(new Reader(counter));
				executor.execute(new Writer(counter));
			}
			
			try
			{
				executor.awaitTermination(10, TimeUnit.SECONDS);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			} finally {
				executor.shutdown();
			}
		}
	}
	
	public static void publish(long end)
	{
		synchronized (rounds[round])
		{
			if (rounds[round] == Boolean.FALSE)
			{
				System.out.println(end-start);
				
				rounds[round] = Boolean.TRUE;
				
				executor.shutdownNow();
			}
		}
	}
	
}
