package play.learn.java.ForkJoinTask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * http://www.javaspecialists.eu/archive/Issue220b.html
 *
 */
public class LearnCommonPool {
	
	private static Map<String, Integer> map = new ConcurrentHashMap<String, Integer>();
	
	public static void main(String[] args) {
		System.out.println(IntStream.range(1, 10000).parallel().filter(LearnCommonPool::isPrime).count());
		map.forEach((k, v) -> System.out.println(k + ": " + v));
		
		System.out.println(ForkJoinPool.commonPool().invoke(new PrimeSearcher(1, 10_000)));
	}
	
	public static boolean isPrime(int n) {
		map.compute(Thread.currentThread().getName(), (k,v) -> v == null ? 1 : v+ 1);
		if (n % 2 == 0) return false;
		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	 private static class PrimeSearcher extends RecursiveTask<Integer> {
		    private static final int THRESHOLD = 10000;
		    private final int from;
		    private final int to;

		    public PrimeSearcher(int from, int to) {
		      this.from = from;
		      this.to = to;
		    }

		    protected Integer compute() {
		      if (to - from < THRESHOLD) {
		        return serialCount();
		      }
		      int from0 = from;
		      int to0 = from + (to - from) / 2;
		      int from1 = to0 + 1;
		      int to1 = to;
		      PrimeSearcher ps0 = new PrimeSearcher(from0, to0);
		      PrimeSearcher ps1 = new PrimeSearcher(from1, to1);
		      ps0.fork();
		      return ps1.invoke() + ps0.join();
		    }

		    private Integer serialCount() {
		      int count = 0;
		      for (int i = from; i < to; i++) {
		        if (LearnCommonPool.isPrime(i)) count++;
		      }
		      return count;
		    }
		  }

}
