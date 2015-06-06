package play.learn.java.annotation;

public class LearnRepeatingAnnotations {
	
	@Schedule(dayOfMonth="last"	)
	@Schedule(dayOfWeek="Fri", hour=23)
	public void doPeriodicCleanup() {
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
