package play.learn.java.design.producer_consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// https://java-design-patterns.com/patterns/producer-consumer/
public class ProducerConsumerDemo {
	public static void main(String[] args) {

		ItemQueue queue = new ItemQueue();

		ExecutorService executorService = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 2; i++) {

			final Producer producer = new Producer("Producer_" + i, queue);
			executorService.submit(() -> {
				while (true) {
					producer.produce();
				}
			});
		}

		for (int i = 0; i < 3; i++) {
			final Consumer consumer = new Consumer("Consumer_" + i, queue);
			executorService.submit(() -> {
				while (true) {
					consumer.consume();
				}
			});
		}

		executorService.shutdown();
		try {
			executorService.awaitTermination(10, TimeUnit.SECONDS);
			executorService.shutdownNow();
		} catch (InterruptedException e) {
			System.out.println("Error waiting for ExecutorService shutdown");
		}
	}
}
