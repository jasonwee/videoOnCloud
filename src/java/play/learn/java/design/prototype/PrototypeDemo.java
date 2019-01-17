package play.learn.java.design.prototype;

// https://java-design-patterns.com/patterns/prototype/
public class PrototypeDemo {

	public static void main(String[] args) {
		HeroFactory factory;
		Mage mage;
		Warlord warlord;
		Beast beast;

		factory = new HeroFactoryImpl(new ElfMage("cooking"), new ElfWarlord("cleaning"), new ElfBeast("protecting"));
		mage = factory.createMage();
		warlord = factory.createWarlord();
		beast = factory.createBeast();
		System.out.println(mage.toString());
		System.out.println(warlord.toString());
		System.out.println(beast.toString());

		factory = new HeroFactoryImpl(new OrcMage("axe"), new OrcWarlord("sword"), new OrcBeast("laser"));
		mage = factory.createMage();
		warlord = factory.createWarlord();
		beast = factory.createBeast();
		System.out.println(mage.toString());
		System.out.println(warlord.toString());
		System.out.println(beast.toString());
	}

}
