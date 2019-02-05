package play.learn.java.design.poison_pill;

public class Consumer {
	private final MqSubscribePoint queue;
	private final String name;

	public Consumer(String name, MqSubscribePoint queue) {
		this.name = name;
		this.queue = queue;
	}

	/**
	 * Consume message
	 */
	public void consume() {
		while (true) {
			Message msg;
			try {
				msg = queue.take();
				if (Message.POISON_PILL.equals(msg)) {
					System.out.printf("Consumer %s receive request to terminate.", name);
					break;
				}
			} catch (InterruptedException e) {
				// allow thread to exit
				System.err.println("Exception caught. " + e);
				return;
			}

			String sender = msg.getHeader(Message.Headers.SENDER);
			String body = msg.getBody();
			System.out.printf("Message [%s] from [%s] received by [%s]", body, sender, name);
		}
	}
}
