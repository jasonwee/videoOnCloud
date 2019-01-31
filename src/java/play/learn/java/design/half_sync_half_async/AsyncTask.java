package play.learn.java.design.half_sync_half_async;

import java.util.concurrent.Callable;


public interface AsyncTask<O> extends Callable<O> {
	/**
	 * Is called in context of caller thread before call to {@link #call()}. Large
	 * tasks should not be performed in this method as it will block the caller
	 * thread. Small tasks such as validations can be performed here so that the
	 * performance penalty of context switching is not incurred in case of invalid
	 * requests.
	 */
	void onPreCall();

	/**
	 * A callback called after the result is successfully computed by
	 * {@link #call()}. In our implementation this method is called in context of
	 * background thread but in some variants, such as Android where only UI thread
	 * can change the state of UI widgets, this method is called in context of UI
	 * thread.
	 */
	void onPostCall(O result);

	/**
	 * A callback called if computing the task resulted in some exception. This
	 * method is called when either of {@link #call()} or {@link #onPreCall()} throw
	 * any exception.
	 * 
	 * @param throwable
	 *            error cause
	 */
	void onError(Throwable throwable);

	/**
	 * This is where the computation of task should reside. This method is called in
	 * context of background thread.
	 */
	@Override
	O call() throws Exception;
}
