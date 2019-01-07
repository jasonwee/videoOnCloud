package play.learn.java.design.execute_around;

import java.io.IOException;

public class ExecuteAroundDemo {

	public static void main(String[] args) throws IOException {

		FileWriterAction writeHello = writer -> {
			writer.write("Hello");
			writer.append(" ");
			writer.append("there!");
		};
		new SimpleFileWriter("testfile.txt", writeHello);
	}

}
