package play.learn.java.design.module;

import java.io.PrintStream;

public class ConsoleLoggerModule {

	  private static ConsoleLoggerModule singleton = null;

	  public PrintStream output = null;
	  public PrintStream error = null;

	  private ConsoleLoggerModule() {}

	  /**
	   * Static method to get single instance of class
	   * 
	   * @return singleton instance of ConsoleLoggerModule
	   */
	  public static ConsoleLoggerModule getSingleton() {

	    if (ConsoleLoggerModule.singleton == null) {
	      ConsoleLoggerModule.singleton = new ConsoleLoggerModule();
	    }

	    return ConsoleLoggerModule.singleton;
	  }

	  /**
	   * Following method performs the initialization
	   */
	  public ConsoleLoggerModule prepare() {

	    System.out.println("ConsoleLoggerModule::prepare();");

	    this.output = new PrintStream(System.out);
	    this.error = new PrintStream(System.err);

	    return this;
	  }

	  /**
	   * Following method performs the finalization
	   */
	  public void unprepare() {

	    if (this.output != null) {

	      this.output.flush();
	      this.output.close();
	    }

	    if (this.error != null) {

	      this.error.flush();
	      this.error.close();
	    }

	    System.out.println("ConsoleLoggerModule::unprepare();");
	  }

	  /**
	   * Used to print a message
	   * 
	   * @param value will be printed on console
	   */
	  public void printString(final String value) {
	    this.output.println(value);
	  }

	  /**
	   * Used to print a error message
	   * 
	   * @param value will be printed on error console
	   */
	  public void printErrorString(final String value) {
	    this.error.println(value);
	  }

}
