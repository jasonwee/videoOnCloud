package play.learn.java.design.execute_around;

import java.io.FileWriter;
import java.io.IOException;

public class SimpleFileWriter {
	
	  /**
	   * Constructor
	   */
	  public SimpleFileWriter(String filename, FileWriterAction action) throws IOException {
	    try (FileWriter writer = new FileWriter(filename)) {
	      action.writeFile(writer);
	    }
	  }
}
