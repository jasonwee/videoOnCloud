package play.learn.java.design.module;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class FileLoggerModule {

	private static FileLoggerModule singleton = null;

	private static final String OUTPUT_FILE = "output.txt";
	private static final String ERROR_FILE = "error.txt";

	public PrintStream output = null;
	public PrintStream error = null;

	private FileLoggerModule() {
	}

	/**
	 * Static method to get single instance of class
	 * 
	 * @return singleton instance of FileLoggerModule
	 */
	public static FileLoggerModule getSingleton() {

		if (FileLoggerModule.singleton == null) {
			FileLoggerModule.singleton = new FileLoggerModule();
		}

		return FileLoggerModule.singleton;
	}

	/**
	 * Following method performs the initialization
	 * 
	 * @throws FileNotFoundException
	 *             if program is not able to find log files (output.txt and
	 *             error.txt)
	 */
	public FileLoggerModule prepare() throws FileNotFoundException {

		System.out.println("FileLoggerModule::prepare();");

		this.output = new PrintStream(new FileOutputStream(OUTPUT_FILE));
		this.error = new PrintStream(new FileOutputStream(ERROR_FILE));

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

		System.out.println("FileLoggerModule::unprepare();");
	}

	/**
	 * Used to print a message
	 * 
	 * @param value
	 *            will be printed in file
	 */
	public void printString(final String value) {
		this.output.println(value);
	}

	/**
	 * Used to print a error message
	 * 
	 * @param value
	 *            will be printed on error file
	 */
	public void printErrorString(final String value) {
		this.error.println(value);
	}

}
