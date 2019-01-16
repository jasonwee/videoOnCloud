package play.learn.java.design.module;

import java.io.FileNotFoundException;

// https://java-design-patterns.com/patterns/module/
public class ModuleDemo {
	
	  public static FileLoggerModule fileLoggerModule;
	  public static ConsoleLoggerModule consoleLoggerModule;

	  /**
	   * Following method performs the initialization
	   * 
	   * @throws FileNotFoundException if program is not able to find log files (output.txt and
	   *         error.txt)
	   */
	  public static void prepare() throws FileNotFoundException {

	    /* Create new singleton objects and prepare their modules */
	    fileLoggerModule = FileLoggerModule.getSingleton().prepare();
	    consoleLoggerModule = ConsoleLoggerModule.getSingleton().prepare();
	  }

	  /**
	   * Following method performs the finalization
	   */
	  public static void unprepare() {

	    /* Close all resources */
	    fileLoggerModule.unprepare();
	    consoleLoggerModule.unprepare();
	  }

	  /**
	   * Following method is main executor
	   * 
	   * @param args for providing default program arguments
	   */
	  public static void execute(final String... args) {

	    /* Send logs on file system */
	    fileLoggerModule.printString("Message");
	    fileLoggerModule.printErrorString("Error");

	    /* Send logs on console */
	    consoleLoggerModule.printString("Message");
	    consoleLoggerModule.printErrorString("Error");
	  }

	  /**
	   * Program entry point.
	   * 
	   * @param args command line args.
	   * @throws FileNotFoundException if program is not able to find log files (output.txt and
	   *         error.txt)
	   */
	  public static void main(final String... args) throws FileNotFoundException {
	    prepare();
	    execute(args);
	    unprepare();
	  }
	
	

}
