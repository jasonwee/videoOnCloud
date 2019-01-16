package play.learn.java.design.property;

import play.learn.java.design.property.Character.Type;


// https://java-design-patterns.com/patterns/property/
public class PropertyDemo {

	public static void main(String[] args) {
		/* set up */
		Prototype charProto = new Character();
		charProto.set(Stats.STRENGTH, 10);
		charProto.set(Stats.AGILITY, 10);
		charProto.set(Stats.ARMOR, 10);
		charProto.set(Stats.ATTACK_POWER, 10);

		Character mageProto = new Character(Type.MAGE, charProto);
		mageProto.set(Stats.INTELLECT, 15);
		mageProto.set(Stats.SPIRIT, 10);

		Character warProto = new Character(Type.WARRIOR, charProto);
		warProto.set(Stats.RAGE, 15);
		warProto.set(Stats.ARMOR, 15); // boost default armor for warrior

		Character rogueProto = new Character(Type.ROGUE, charProto);
		rogueProto.set(Stats.ENERGY, 15);
		rogueProto.set(Stats.AGILITY, 15); // boost default agility for rogue

		/* usage */
		Character mag = new Character("Player_1", mageProto);
		mag.set(Stats.ARMOR, 8);
		System.out.println(mag.toString());

		Character warrior = new Character("Player_2", warProto);
		System.out.println(warrior.toString());

		Character rogue = new Character("Player_3", rogueProto);
		System.out.println(rogue.toString());

		Character rogueDouble = new Character("Player_4", rogue);
		rogueDouble.set(Stats.ATTACK_POWER, 12);
		System.out.println(rogueDouble.toString());
	}

}
