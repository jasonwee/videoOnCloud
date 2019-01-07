package play.learn.java.design.event_aggregator;

import java.util.ArrayList;
import java.util.List;

// https://java-design-patterns.com/patterns/event-aggregator/
public class EventAggregatorDemo {

	public static void main(String[] args) {
		
	    KingJoffrey kingJoffrey = new KingJoffrey();
	    KingsHand kingsHand = new KingsHand(kingJoffrey);

	    List<EventEmitter> emitters = new ArrayList<>();
	    emitters.add(kingsHand);
	    emitters.add(new LordBaelish(kingsHand));
	    emitters.add(new LordVarys(kingsHand));
	    emitters.add(new Scout(kingsHand));

	    for (Weekday day : Weekday.values()) {
	      for (EventEmitter emitter : emitters) {
	        emitter.timePasses(day);
	      }
	    }

	}

}
