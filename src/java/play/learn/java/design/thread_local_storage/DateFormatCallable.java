package play.learn.java.design.thread_local_storage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.Callable;

public class DateFormatCallable implements Callable<Result> {

	// class variables (members)
	private ThreadLocal<DateFormat> df; // TLTL
	// private DateFormat df; //NTLNTL

	private String dateValue; // for dateValue Thread Local not needed

	/**
	 * The date format and the date value are passed to the constructor
	 * 
	 * @param inDateFormat
	 *            string date format string, e.g. "dd/MM/yyyy"
	 * @param inDateValue
	 *            string date value, e.g. "21/06/2016"
	 */
	public DateFormatCallable(String inDateFormat, String inDateValue) {
		final String idf = inDateFormat; // TLTL
		this.df = new ThreadLocal<DateFormat>() { // TLTL
			@Override // TLTL
			protected DateFormat initialValue() { // TLTL
				return new SimpleDateFormat(idf); // TLTL
			} // TLTL
		}; // TLTL
		// this.df = new SimpleDateFormat(inDateFormat); //NTLNTL
		this.dateValue = inDateValue;
	}

	/**
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Result call() {
		System.out.println(Thread.currentThread() + " started executing...");
		Result result = new Result();

		// Convert date value to date 5 times
		for (int i = 1; i <= 5; i++) {
			try {
				// this is the statement where it is important to have the
				// instance of SimpleDateFormat locally
				// Create the date value and store it in dateList
				result.getDateList().add(this.df.get().parse(this.dateValue)); // TLTL
				// result.getDateList().add(this.df.parse(this.dateValue)); //NTLNTL
			} catch (Exception e) {
				// write the Exception to a list and continue work
				result.getExceptionList().add(e.getClass() + ": " + e.getMessage());
			}

		}

		System.out.println(Thread.currentThread() + " finished processing part of the thread");

		return result;
	}

}
