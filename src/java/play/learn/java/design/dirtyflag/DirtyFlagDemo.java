package play.learn.java.design.dirtyflag;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// https://java-design-patterns.com/patterns/dirty-flag/
public class DirtyFlagDemo {
	
	public void run() {
		
	    final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	    executorService.scheduleAtFixedRate(new Runnable() {
	      @Override
	      public void run() {
	        World world = new World();
	        List<String> countries = world.fetch();
	        System.out.println("Our world currently has the following countries:-");
	        for (String country : countries) {
	          System.out.println("\t" + country);
	        }
	      }
	    }, 0, 15, TimeUnit.SECONDS); // Run at every 15 seconds.

		
	}

	public static void main(String[] args) {
		DirtyFlagDemo demo = new DirtyFlagDemo();
		demo.run();

	}

}
