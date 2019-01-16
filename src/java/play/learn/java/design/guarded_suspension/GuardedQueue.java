package play.learn.java.design.guarded_suspension;

import java.util.LinkedList;
import java.util.Queue;

public class GuardedQueue {

	  private final Queue<Integer> sourceList;

	  public GuardedQueue() {
	    this.sourceList = new LinkedList<>();
	  }

	  /**
	   * @return last element of a queue if queue is not empty
	   */
	  public synchronized Integer get() {
	    while (sourceList.isEmpty()) {
	      try {
	        System.out.println("waiting");
	        wait();
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	    }
	    System.out.println("getting");
	    return sourceList.peek();
	  }

	  /**
	   * @param e number which we want to put to our queue
	   */
	  public synchronized void put(Integer e) {
	    System.out.println("putting");
	    sourceList.add(e);
	    System.out.println("notifying");
	    notify();
	  }

}
