package play.learn.java.design.thread_local_storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Result {
	// A list to collect the date values created in one thread
	private List<Date> dateList = new ArrayList<Date>();

	// A list to collect Exceptions thrown in one threads (should be none in
	// this example)
	private List<String> exceptionList = new ArrayList<String>();

	/**
	 * 
	 * @return List of date values collected within an thread execution
	 */
	public List<Date> getDateList() {
		return dateList;
	}

	/**
	 * 
	 * @return List of exceptions thrown within an thread execution
	 */
	public List<String> getExceptionList() {
		return exceptionList;
	}
}
