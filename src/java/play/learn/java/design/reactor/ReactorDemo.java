package play.learn.java.design.reactor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// https://java-design-patterns.com/patterns/reactor/
public class ReactorDemo {

	private NioReactor reactor;
	private List<AbstractNioChannel> channels = new ArrayList<>();
	private Dispatcher dispatcher;

	/**
	   * Creates an instance of App which will use provided dispatcher for dispatching events on
	   * reactor.
	   * 
	   * @param dispatcher the dispatcher that will be used to dispatch events.
	   */
	  public ReactorDemo(Dispatcher dispatcher) {
	    this.dispatcher = dispatcher;
	  }

	/**
	 * App entry.
	 */
	public static void main(String[] args) throws IOException {
		new ReactorDemo(new ThreadPoolDispatcher(2)).start();
	}

	/**
	 * Starts the NIO reactor.
	 *
	 * @throws IOException
	 *             if any channel fails to bind.
	 */
	public void start() throws IOException {
		/*
		 * The application can customize its event dispatching mechanism.
		 */
		reactor = new NioReactor(dispatcher);

		/*
		 * This represents application specific business logic that dispatcher will call
		 * on appropriate events. These events are read events in our example.
		 */
		LoggingHandler loggingHandler = new LoggingHandler();

		/*
		 * Our application binds to multiple channels and uses same logging handler to
		 * handle incoming log requests.
		 */
		reactor.registerChannel(tcpChannel(6666, loggingHandler)).registerChannel(tcpChannel(6667, loggingHandler))
				.registerChannel(udpChannel(6668, loggingHandler)).start();
	}

	/**
	 * Stops the NIO reactor. This is a blocking call.
	 * 
	 * @throws InterruptedException
	 *             if interrupted while stopping the reactor.
	 * @throws IOException
	 *             if any I/O error occurs
	 */
	public void stop() throws InterruptedException, IOException {
		reactor.stop();
		dispatcher.stop();
		for (AbstractNioChannel channel : channels) {
			channel.getJavaChannel().close();
		}
	}

	private AbstractNioChannel tcpChannel(int port, ChannelHandler handler) throws IOException {
		NioServerSocketChannel channel = new NioServerSocketChannel(port, handler);
		channel.bind();
		channels.add(channel);
		return channel;
	}

	private AbstractNioChannel udpChannel(int port, ChannelHandler handler) throws IOException {
		NioDatagramChannel channel = new NioDatagramChannel(port, handler);
		channel.bind();
		channels.add(channel);
		return channel;
	}

}
