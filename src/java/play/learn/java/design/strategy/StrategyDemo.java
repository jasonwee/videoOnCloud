package play.learn.java.design.strategy;

// https://java-design-patterns.com/patterns/strategy/
public class StrategyDemo {

	public static void main(String[] args) {
		// GoF Strategy pattern
		System.out.println("Green dragon spotted ahead!");
		DragonSlayer dragonSlayer = new DragonSlayer(new MeleeStrategy());
		dragonSlayer.goToBattle();
		System.out.println("Red dragon emerges.");
		dragonSlayer.changeStrategy(new ProjectileStrategy());
		dragonSlayer.goToBattle();
		System.out.println("Black dragon lands before you.");
		dragonSlayer.changeStrategy(new SpellStrategy());
		dragonSlayer.goToBattle();

		// Java 8 Strategy pattern
		System.out.println("Green dragon spotted ahead!");
		dragonSlayer = new DragonSlayer(() -> System.out.println("With your Excalibur you severe the dragon's head!"));
		dragonSlayer.goToBattle();
		System.out.println("Red dragon emerges.");
		dragonSlayer.changeStrategy(
				() -> System.out.println("You shoot the dragon with the magical crossbow and it falls dead on the ground!"));
		dragonSlayer.goToBattle();
		System.out.println("Black dragon lands before you.");
		dragonSlayer.changeStrategy(
				() -> System.out.println("You cast the spell of disintegration and the dragon vaporizes in a pile of dust!"));
		dragonSlayer.goToBattle();
	}

}
