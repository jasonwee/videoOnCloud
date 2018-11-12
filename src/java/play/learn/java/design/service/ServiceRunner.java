package play.learn.java.design.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// https://dzone.com/articles/how-write-better-pojo-services
public class ServiceRunner {
	
	   private static Logger logger = LoggerFactory.getLogger(ServiceRunner.class);

	    public static void main(String[] args) {
	        ServiceRunner main = new ServiceRunner();
	        main.run(args);
	    }

	    public void run(String[] args) {
	        if (args.length < 1)
	            throw new RuntimeException("Missing service class name as argument.");

	        String serviceClassName = args[0];
	        try {
	            logger.debug("Creating " + serviceClassName);
	            Class<?> serviceClass = Class.forName(serviceClassName);
	            if (!Service.class.isAssignableFrom(serviceClass)) {
	                throw new RuntimeException("Service class " + serviceClassName + " did not implements " + Service.class.getName());
	            }
	            Object serviceObject = serviceClass.newInstance();
	            Service service = (Service)serviceObject;

	            registerShutdownHook(service);

	            logger.debug("Starting service " + service);
	            service.init();
	            service.start();
	            logger.info(service + " started.");

	            synchronized(this) {
	                this.wait();
	            }
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to create and run " + serviceClassName, e);
	        }
	    }

	    private void registerShutdownHook(final Service service) {
	        Runtime.getRuntime().addShutdownHook(new Thread() {
	            public void run() {
	                logger.debug("Stopping service " + service);
	                service.stop();
	                service.destroy();
	                logger.info(service + " stopped.");
	            }
	        });
	    }

}
