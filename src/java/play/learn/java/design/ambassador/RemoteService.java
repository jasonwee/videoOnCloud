package play.learn.java.design.ambassador;

import static java.lang.Thread.sleep;


public class RemoteService implements RemoteServiceInterface {
	  static final int THRESHOLD = 200;
	  
	  private static RemoteService service = null;
	  private final RandomProvider randomProvider;

	  static synchronized RemoteService getRemoteService() {
	    if (service == null) {
	      service = new RemoteService();
	    }
	    return service;
	  }

	  private RemoteService() {
	    this(Math::random);
	  }

	  /**
	   * This constuctor is used for testing purposes only.
	   */
	  RemoteService(RandomProvider randomProvider) {
	    this.randomProvider = randomProvider;
	  }
	  /**
	   * Remote function takes a value and multiplies it by 10 taking a random amount of time.
	   * Will sometimes return -1. This imitates connectivity issues a client might have to account for.
	   * @param value integer value to be multiplied.
	   * @return if waitTime is less than {@link RemoteService#THRESHOLD}, it returns value * 10,
	   *     otherwise {@link RemoteServiceInterface#FAILURE}.
	   */
	  @Override
	  public long doRemoteFunction(int value) {

	    long waitTime = (long) Math.floor(randomProvider.random() * 1000);

	    try {
	      sleep(waitTime);
	    } catch (InterruptedException e) {
	      System.out.println("Thread sleep state interrupted "+ e);
	    }
	    return waitTime <= THRESHOLD ? value * 10 : FAILURE;
	  }
}
