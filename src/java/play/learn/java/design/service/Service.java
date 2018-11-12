package play.learn.java.design.service;

public interface Service {

	void init();

	void start();

	void stop();

	void destroy();

	boolean isInited();

	boolean isStarted();

}
