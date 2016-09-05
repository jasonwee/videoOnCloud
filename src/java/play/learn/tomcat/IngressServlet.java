package play.learn.tomcat;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

@javax.servlet.annotation.WebServlet (
	// servet name 
	name = "",
	// servlet url pattern
	value = ("/"),
	// async support needed
	asyncSupported = true
)
public class IngressServlet extends HttpServlet {

	/**
	 * Simply spawn a new thread (from the app server's pool) for every new async request.
	 * Will consume a lot more threads for many concurrent requests. 
	 */
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		
		// create the async context, otherwise getAsyncContext() will be null
		final AsyncContext ctx = req.startAsync();
		
		// set the timeout, means the runnable must complete within 30seconds or else timeout will be throw.
		ctx.setTimeout(30000);
		
		// attach listener to respond to lifecycle events of this AsyncContext
		ctx.addListener(new AsyncListener() {
			
			@Override
			public void onTimeout(AsyncEvent arg0) throws IOException {
				log("onTimeout called");
			}
			
			@Override
			public void onStartAsync(AsyncEvent arg0) throws IOException {
				log("onStartAsync called");
			}
			
			@Override
			public void onError(AsyncEvent arg0) throws IOException {
				log("onError called");
			}
			
			@Override
			public void onComplete(AsyncEvent arg0) throws IOException {
				log("onComplete called");
			}
		});
		
		ctx.start(new Runnable() {
			public void run() {
				try {
					ctx.getResponse().getWriter().write(MessageFormat.format("processing task, id: {0}", Thread.currentThread().getId()));
				} catch (IOException e) {
					log("Problem processing task", e);
				}
				
				ctx.complete();
			}
		});
	}
	
}
