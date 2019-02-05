package play.learn.java.design.poison_pill;

public interface MqSubscribePoint {
	Message take() throws InterruptedException;

}
