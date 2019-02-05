package play.learn.java.design.poison_pill;

// https://java-design-patterns.com/patterns/poison-pill/
public class PoisonPillDemo {

	public static void main(String[] args) {
		MessageQueue queue = new SimpleMessageQueue(10000);

		final Producer producer = new Producer("PRODUCER_1", queue);
		final Consumer consumer = new Consumer("CONSUMER_1", queue);

		new Thread() {
			@Override
			public void run() {
				consumer.consume();
			}
		}.start();

		new Thread() {
			@Override
			public void run() {
				producer.send("hand shake");
				producer.send("some very important information");
				producer.send("bye!");
				producer.stop();
			}
		}.start();
	}
}
