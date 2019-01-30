package play.learn.java.design.flyweight;

public class PoisonPotion implements Potion {

	@Override
	public void drink() {
		System.out.printf("Urgh! This is poisonous. (Potion={})", System.identityHashCode(this));
	}
}
