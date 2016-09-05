package play.learn.tomcat;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;

/**
 * this is better than before, got start and stop
 *
 */
@javax.servlet.annotation.WebServlet(
		// servlet name
		name = "complex",
		// servlet url pattern
		value = {"/complex"},
		// async support needed
		asyncSupported = true,
		// servlet init params
		initParams = {
				@WebInitParam(name = "threadpoolsize", value= "3")
		}
)
public class IngressServletV1 extends HttpServlet {
	
	public static final AtomicInteger counter = new AtomicInteger(0);
	public static final int CALLBACK_TIMEOUT = 60000;
	public static final int MAX_SIMULATED_TASK_LENGTH_MS = 5000;
	
	/** executor svc */
	private ExecutorService exec;
	
	
	@Override
	public void init() throws ServletException {
		// TODO maybe should be 1.5 times of the tomcat max thread pool.
		int size = Integer.parseInt(getInitParameter("threadpoolsize"));
		exec = Executors.newFixedThreadPool(size);
	}
	
	@Override
	public void destroy() {
		exec.shutdown();
	}

	/**
	 * Spawn the task on the provided {@link #exec} object.
	 * This limits the max number of threads in the pool that can be spawned and puts
	 * a ceiling on the max number of threads that can be used to the init param
	 * "threadpoolsize".
	 */
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// create the async context, otherwise getAsyncContext() will be null
		final AsyncContext ctx = req.startAsync();
		
		// set the timeout
		ctx.setTimeout(CALLBACK_TIMEOUT);
		
		// attach listener to response to lifecycle events of this asynccontext
		ctx.addListener(new AsyncListener() {
			
			// timeout has occured in async task... handle it!!
			@Override
			public void onTimeout(AsyncEvent event) throws IOException {
				log("onTiemout called");
				log(event.toString());
				ctx.getResponse().getWriter().write("timeout!!");
				ctx.complete();
			}
			
			// async context has started, nothing to do
			@Override
			public void onStartAsync(AsyncEvent arg0) throws IOException {
			}
			
			// this never get called - error has occured in async task... handle it 
			@Override
			public void onError(AsyncEvent event) throws IOException {
				log("onError called");
				log(event.toString());
				ctx.getResponse().getWriter().write("ERROR!!");
				ctx.complete();
			}
			
			// complete() has already been called on the async context, nothing to do 
			@Override
			public void onComplete(AsyncEvent event) throws IOException { }
		});
		
		// simulate error only - this does not cause onError - causes network erro on client side
		if (counter.addAndGet(1) < 5) {
			throw new IndexOutOfBoundsException("Simulated error");
		} else {
			// spawn some task to be run in executor
			enqueLongRunningTask(ctx);
		}
	}
	
	/**
	 * if something goes wrong in the task, it simply causes timeout condition that causes
	 * the async context listener to be invoked (after the fact)
	 * 
	 * if the {@link AsyncContext@getResponse()} is null, that means this context has 
	 * already timedout (and context listener has been invoked).
	 */
	private void enqueLongRunningTask(final AsyncContext ctx) {
		exec.execute(new Runnable() {
			
			@Override
			public void run() {
				
				try {
				
				// simulate random delay
				int delay = new Random().nextInt(MAX_SIMULATED_TASK_LENGTH_MS);
				Thread.currentThread().sleep(delay);
				
				// response is null if the context has already timedout
				// (at this point the app server has called the listener already)
				ServletResponse response = ctx.getResponse();
				if (response != null) {
					response.getWriter().write(MessageFormat.format("processing task in id {0} delay : {1}", Thread.currentThread().getId(), delay));
					ctx.complete();
				} else {
					throw new IllegalStateException("Response object from context is null!");
				}
				
				
				} catch (Exception e) {
					log("Problem processing task", e);
					e.printStackTrace();
				}
			}		
		});
	}


	

}
