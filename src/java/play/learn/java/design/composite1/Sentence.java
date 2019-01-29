package play.learn.java.design.composite1;

import java.util.List;


public class Sentence extends LetterComposite {
	/**
	 * Constructor
	 */
	public Sentence(List<Word> words) {
		for (Word w : words) {
			this.add(w);
		}
	}

	@Override
	protected void printThisAfter() {
		System.out.print(".");
	}
}
