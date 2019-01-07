package play.learn.java.design.event_aggregator;

public class LordBaelish extends EventEmitter {

	public LordBaelish() {
	}

	public LordBaelish(EventObserver obs) {
		super(obs);
	}

	@Override
	public void timePasses(Weekday day) {
		if (day.equals(Weekday.FRIDAY)) {
			notifyObservers(Event.STARK_SIGHTED);
		}
	}

}
