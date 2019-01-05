package play.learn.java.event_aggregator;

public class Scout extends EventEmitter {

	public Scout() {
	}

	public Scout(EventObserver obs) {
		super(obs);
	}

	@Override
	public void timePasses(Weekday day) {
		if (day.equals(Weekday.TUESDAY)) {
			notifyObservers(Event.WARSHIPS_APPROACHING);
		}
	}
}
