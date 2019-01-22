package play.learn.java.design.strategy;

public class MeleeStrategy implements DragonSlayingStrategy {

	@Override
	public void execute() {
		System.out.println("With your Excalibur you sever the dragon's head!");
	}

}
