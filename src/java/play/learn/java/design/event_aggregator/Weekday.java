package play.learn.java.design.event_aggregator;

public enum Weekday {

	MONDAY("Monday"), TUESDAY("Tuesday"), 
	WEDNESDAY("Wednesday"), THURSDAY("Thursday"), 
	FRIDAY("Friday"), SATURDAY("Saturday"), 
	SUNDAY("Sunday");

	private String description;

	Weekday(String description) {
		this.description = description;
	}

	public String toString() {
		return description;
	}

}
