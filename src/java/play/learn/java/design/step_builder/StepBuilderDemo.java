package play.learn.java.design.step_builder;

// https://java-design-patterns.com/patterns/step-builder/
public class StepBuilderDemo {
	public static void main(String[] args) {

		Character warrior = CharacterStepBuilder.newBuilder().name("Amberjill").fighterClass("Paladin")
				.withWeapon("Sword").noAbilities().build();

		System.out.println(warrior.toString());

		Character mage = CharacterStepBuilder.newBuilder().name("Riobard").wizardClass("Sorcerer").withSpell("Fireball")
				.withAbility("Fire Aura").withAbility("Teleport").noMoreAbilities().build();

		System.out.println(mage.toString());

		Character thief = CharacterStepBuilder.newBuilder().name("Desmond").fighterClass("Rogue").noWeapon().build();

		System.out.println(thief.toString());
	}
}
