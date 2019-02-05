package play.learn.java.design.poison_pill;

import java.util.Date;


public class Producer {

	private final MqPublishPoint queue;
	private final String name;
	private boolean isStopped;

	/**
	 * Constructor
	 */
	public Producer(String name, MqPublishPoint queue) {
		this.name = name;
		this.queue = queue;
		this.isStopped = false;
	}

	/**
	 * Send message to queue
	 */
	public void send(String body) {
		if (isStopped) {
			throw new IllegalStateException(
					String.format("Producer %s was stopped and fail to deliver requested message [%s].", body, name));
		}
		Message msg = new SimpleMessage();
		msg.addHeader(Message.Headers.DATE, new Date().toString());
		msg.addHeader(Message.Headers.SENDER, name);
		msg.setBody(body);

		try {
			queue.put(msg);
		} catch (InterruptedException e) {
			// allow thread to exit
			System.err.println("Exception caught." + e);
		}
	}

	/**
	 * Stop system by sending poison pill
	 */
	public void stop() {
		isStopped = true;
		try {
			queue.put(Message.POISON_PILL);
		} catch (InterruptedException e) {
			// allow thread to exit
			System.err.println("Exception caught." + e);
		}
	}
}
