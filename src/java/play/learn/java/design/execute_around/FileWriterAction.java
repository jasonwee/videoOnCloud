package play.learn.java.design.execute_around;

import java.io.FileWriter;
import java.io.IOException;

@FunctionalInterface
public interface FileWriterAction {
	  void writeFile(FileWriter writer) throws IOException;
}
