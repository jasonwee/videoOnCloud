package play.learn.java.event_aggregator;

public class LordVarys extends EventEmitter {

	public LordVarys() {
	}

	public LordVarys(EventObserver obs) {
		super(obs);
	}

	@Override
	public void timePasses(Weekday day) {
		if (day.equals(Weekday.SATURDAY)) {
			notifyObservers(Event.TRAITOR_DETECTED);
		}
	}

}
