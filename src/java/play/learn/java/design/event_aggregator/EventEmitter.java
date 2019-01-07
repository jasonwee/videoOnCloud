package play.learn.java.design.event_aggregator;

import java.util.LinkedList;
import java.util.List;

public abstract class EventEmitter {

	private List<EventObserver> observers;

	public EventEmitter() {
		observers = new LinkedList<>();
	}

	public EventEmitter(EventObserver obs) {
		this();
		registerObserver(obs);
	}

	public final void registerObserver(EventObserver obs) {
		observers.add(obs);
	}

	protected void notifyObservers(Event e) {
		for (EventObserver obs : observers) {
			obs.onEvent(e);
		}
	}

	public abstract void timePasses(Weekday day);

}
