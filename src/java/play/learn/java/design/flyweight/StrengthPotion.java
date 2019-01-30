package play.learn.java.design.flyweight;

public class StrengthPotion implements Potion {

	@Override
	public void drink() {
		System.out.printf("You feel strong. (Potion=%s)", System.identityHashCode(this));
	}
}
