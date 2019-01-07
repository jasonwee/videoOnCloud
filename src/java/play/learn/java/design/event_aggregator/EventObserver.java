package play.learn.java.design.event_aggregator;

public interface EventObserver {

	void onEvent(Event e);

}