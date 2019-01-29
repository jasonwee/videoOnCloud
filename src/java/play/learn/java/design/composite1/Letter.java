package play.learn.java.design.composite1;

public class Letter extends LetterComposite {

	private char c;

	public Letter(char c) {
		this.c = c;
	}

	@Override
	protected void printThisBefore() {
		System.out.print(c);
	}

}
