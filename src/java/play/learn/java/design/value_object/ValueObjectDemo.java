package play.learn.java.design.value_object;

// https://java-design-patterns.com/patterns/value-object/
public class ValueObjectDemo {
	public static void main(String[] args) {
		HeroStat statA = HeroStat.valueOf(10, 5, 0);
		HeroStat statB = HeroStat.valueOf(10, 5, 0);
		HeroStat statC = HeroStat.valueOf(5, 1, 8);

		System.out.println(statA.toString());

		System.out.printf("Is statA and statB equal : %s", statA.equals(statB));
		System.out.printf("Is statA and statC equal : %s", statA.equals(statC));
	}
}
