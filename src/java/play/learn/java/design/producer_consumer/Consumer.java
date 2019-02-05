package play.learn.java.design.producer_consumer;

public class Consumer {
	private final ItemQueue queue;

	private final String name;

	public Consumer(String name, ItemQueue queue) {
		this.name = name;
		this.queue = queue;
	}

	/**
	 * Consume item from the queue
	 */
	public void consume() throws InterruptedException {

		Item item = queue.take();
		System.out.printf("Consumer [%s] consume item [%s] produced by [%s]", name, item.getId(), item.getProducer());

	}
}
