package play.learn.java.design.service;

public class HelloService extends AbstractService {
	
	   public void initService() {
	        logger.info(this + " inited.");
	    }
	    public void startService() {
	        logger.info(this + " started.");
	    }
	    public void stopService() {
	        logger.info(this + " stopped.");
	    }
	    public void destroyService() {
	        logger.info(this + " destroyed.");
	    }

}
