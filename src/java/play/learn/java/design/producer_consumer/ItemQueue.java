package play.learn.java.design.producer_consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ItemQueue {
	private BlockingQueue<Item> queue;

	public ItemQueue() {

		queue = new LinkedBlockingQueue<>(5);
	}

	public void put(Item item) throws InterruptedException {

		queue.put(item);
	}

	public Item take() throws InterruptedException {

		return queue.take();
	}
}
