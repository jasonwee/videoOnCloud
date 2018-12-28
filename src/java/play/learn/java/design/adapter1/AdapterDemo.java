package play.learn.java.design.adapter1;

// https://java-design-patterns.com/patterns/adapter/
public class AdapterDemo {

	public static void main(String[] args) {
		Captain captain = new Captain(new FishingBoatAdapter());
		captain.row();

	}

}
