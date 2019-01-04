package play.learn.java.design.decorator;

// https://java-design-patterns.com/patterns/decorator/
public class DecoratorDemo {

	public static void main(String[] args) {
		
	    // simple troll
	    System.out.println("A simple looking troll approaches.");
	    Troll troll = new SimpleTroll();
	    troll.attack();
	    troll.fleeBattle();
	    System.out.println("Simple troll power "+troll.getAttackPower()+".\n");

	    // change the behavior of the simple troll by adding a decorator
	    System.out.println("A troll with huge club surprises you.");
	    Troll clubbedTroll = new ClubbedTroll(troll);
	    clubbedTroll.attack();
	    clubbedTroll.fleeBattle();
	    System.out.println("Clubbed troll power "+clubbedTroll.getAttackPower()+".\n");
	}

}
