package play.learn.java.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class LearnFiles {

	public static void main(String[] args) throws IOException {
		
		// added in java8
		BufferedReader br = Files.newBufferedReader(Paths.get("clean/test.txt"));
		
		// added in java8
		BufferedWriter bw = Files.newBufferedWriter(Paths.get("clean/test.txt"), StandardOpenOption.APPEND);
		
		// added in java8
		List<String> lines = Files.readAllLines(Paths.get("clean/test.txt"));
		
		// added in java8
		Files.write(Paths.get("clean/test.txt"), lines, StandardOpenOption.APPEND);
		
		// added in java8
		Stream<Path> path = Files.list(Paths.get("clean/test.txt"));
		
		// added in java8
		path = Files.walk(Paths.get("clean/"), FileVisitOption.FOLLOW_LINKS);
		
		// added in java8
		path = Files.walk(Paths.get("clean/"), 2, FileVisitOption.FOLLOW_LINKS);

		// added in java8
		Files.find(Paths.get("clean/"), 2, (x, y) ->  { if (x.endsWith("txt")) return true; else return false;}, FileVisitOption.FOLLOW_LINKS);
		
		// added in java8
		Files.lines(Paths.get("clean/"));
		
		// added in java8
		Files.lines(Paths.get("clean/"), Charset.defaultCharset());
	}

}
