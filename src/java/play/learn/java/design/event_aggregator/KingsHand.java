package play.learn.java.design.event_aggregator;

public class KingsHand extends EventEmitter implements EventObserver {

	  public KingsHand() {
	  }

	  public KingsHand(EventObserver obs) {
	    super(obs);
	  }

	  @Override
	  public void onEvent(Event e) {
	    notifyObservers(e);
	  }

	  @Override
	  public void timePasses(Weekday day) {
	    // NOP
	  }

}
