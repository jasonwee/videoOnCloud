package play.learn.java.design.flyweight;

public class InvisibilityPotion implements Potion {

	@Override
	public void drink() {
		System.out.printf("You become invisible. (Potion=%s)", System.identityHashCode(this));
	}

}
