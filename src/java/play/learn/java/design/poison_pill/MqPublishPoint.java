package play.learn.java.design.poison_pill;

public interface MqPublishPoint {
	void put(Message msg) throws InterruptedException;

}
