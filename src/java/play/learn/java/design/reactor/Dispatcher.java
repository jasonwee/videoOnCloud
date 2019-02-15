package play.learn.java.design.reactor;

import java.nio.channels.SelectionKey;


public interface Dispatcher {
	/**
	 * This hook method is called when read event occurs on particular channel. The
	 * data read is provided in <code>readObject</code>. The implementation should
	 * dispatch this read event to the associated {@link ChannelHandler} of
	 * <code>channel</code>.
	 * 
	 * <p>
	 * The type of <code>readObject</code> depends on the channel on which data was
	 * received.
	 * 
	 * @param channel
	 *            on which read event occurred
	 * @param readObject
	 *            object read by channel
	 * @param key
	 *            on which event occurred
	 */
	void onChannelReadEvent(AbstractNioChannel channel, Object readObject, SelectionKey key);

	/**
	 * Stops dispatching events and cleans up any acquired resources such as
	 * threads.
	 * 
	 * @throws InterruptedException
	 *             if interrupted while stopping dispatcher.
	 */
	void stop() throws InterruptedException;
}
