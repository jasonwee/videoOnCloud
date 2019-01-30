package play.learn.java.design.flyweight;

public class HealingPotion implements Potion {

	@Override
	public void drink() {
		System.out.printf("You feel healed. (Potion=%s)", System.identityHashCode(this));
	}

}
