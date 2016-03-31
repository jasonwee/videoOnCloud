package play.learn.java.hugesum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.StatUtils;

public class Example {
	
	static List<Integer> hugeLists = null;
	
	public Example() {
		
	}
	
	private int randInt(int min, int max) {
		Random rand = ThreadLocalRandom.current();		
		int randomNum = rand.nextInt((max - min) + 1) + min;		
		return randomNum;
	}
	
	public void populateHugeLists() {
		int size = 100_000_000;
		hugeLists = new ArrayList<Integer>(size);
		
		for (int i = 0; i < size; i++) {
			hugeLists.add(randInt(1, 10));
		}
	}
	
	/**
	 * old school.
	 */
	public void summer101() {
		int sum = 0;
		for (int i : hugeLists) {
			sum += i;
		}
		System.out.println(sum);
	}
	
	public void summerJava8Mapping() {
		int sum = hugeLists.stream().mapToInt(Integer::intValue).sum();
		System.out.println(sum);
	}
	
	public void summerJava8reducer() {
		int sum = hugeLists.stream().reduce(0, (a,b) -> a+b);
		System.out.println(sum);
	}
	
	/**
	 * dont work
	 */
	public void summerViaApacheCommons() {
		double[] sumThemUp = ArrayUtils.toPrimitive(hugeLists.toArray(new Double[hugeLists.size()])); 
		double sums = StatUtils.sum(sumThemUp);
	}
	
	public int summerViaDivider(List<Integer> ints) {
	    int len = ints.size();
	    if (len == 0) return 0;
	    if (len == 1) return ints.get(0);
	    return summerViaDivider(ints.subList(0, len/2)) + summerViaDivider(ints.subList(len/2, len));
	}
	
	public void summerViaJava8LongAdder() {
		LongAdder a = new LongAdder();
		hugeLists.parallelStream().forEach(a::add);
		int sum = a.intValue();
		System.out.println(sum);
	}
	
	public void summerViaFJP() {
	    final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
	    //final ForkJoinPool forkJoinPool = new ForkJoinPool(2);
	    final RecursiveSum recursiveSum = new RecursiveSum(hugeLists, 0, hugeLists.size());
	    long sum = forkJoinPool.invoke(recursiveSum);
	    System.out.println(sum);
	}
	
	  private static class RecursiveSum extends RecursiveTask<Long> {
		    private static final long serialVersionUID = 1L;
		    private static final int THRESHOLD = 1000;
		    private final List<Integer> list;
		    private final int begin;
		    private final int end;

		    public RecursiveSum(List<Integer> list, int begin, int end) {
		      super();
		      this.list = list;
		      this.begin = begin;
		      this.end = end;
		    }

		    @Override
		    protected Long compute() {
		      final int size = end - begin;
		      // if the work to be done is below some threshold, just compute directly.
		      if (size < THRESHOLD) {
		        long sum = 0;
		        for (int i = begin; i < end; i++)
		          sum += list.get(i);
		        return sum;
		      } else {
		        // split the work to other tasks - recursive (that's why it is called recursive task!)
		        final int middle = begin + ((end - begin) / 2);
		        RecursiveSum sum1 = new RecursiveSum(list, begin, middle);
		        // invoke the first portion -> will be invoked in thread pool
		        sum1.fork();
		        RecursiveSum sum2 = new RecursiveSum(list, middle, end);
		        // now do a blocking! compute on the second task and wait for the result of the first task.
		        return sum2.compute() + sum1.join();
		      }
		    }
		  }

	public static void main(String[] args) {
		Example benchmark = new Example();
		benchmark.populateHugeLists();
		
		long t = System.currentTimeMillis();
		benchmark.summer101();
		System.out.println("timespent on summer101 " + (System.currentTimeMillis() - t) + "ms");
		
		t = System.currentTimeMillis();
		benchmark.summerJava8Mapping();
		System.out.println("timespent on summerJava8Mapping " + (System.currentTimeMillis() - t) + "ms");
		
		t = System.currentTimeMillis();
		benchmark.summerJava8reducer();
		System.out.println("timespent on summerJava8reducer " + (System.currentTimeMillis() - t) + "ms");
		
		/*
		t = System.currentTimeMillis();
		benchmark.summerViaApacheCommons();
		System.out.println("timespent on summerViaApacheCommons " + (System.currentTimeMillis() - t) + "ms");
		*/
		
		t = System.currentTimeMillis();
		benchmark.summerViaDivider(hugeLists);
		System.out.println("timespent on summerViaDivider " + (System.currentTimeMillis() - t) + "ms");
		
		t = System.currentTimeMillis();
		benchmark.summerViaJava8LongAdder();
		System.out.println("timespent on summerViaJava8LongAdder " + (System.currentTimeMillis() - t) + "ms");
		
		t = System.currentTimeMillis();
		benchmark.summerViaFJP();
		System.out.println("timespent on summerViaFJP " + (System.currentTimeMillis() - t) + "ms");
		
		/*
		System.out.println(benchmark.hugeLists.size());
		System.out.println(benchmark.hugeLists.get(0));
		System.out.println(benchmark.hugeLists.get(999999));
		*/
	}

}
