package play.learn.java.design.flyweight;

public class HolyWaterPotion implements Potion {

	@Override
	public void drink() {
		System.out.printf("You feel blessed. (Potion=%s)", System.identityHashCode(this));
	}

}
