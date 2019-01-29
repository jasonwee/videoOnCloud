package play.learn.java.design.composite1;

// https://java-design-patterns.com/patterns/composite/
public class CompositeDemo {
	public static void main(String[] args) {
		System.out.println("Message from the orcs: ");

		LetterComposite orcMessage = new Messenger().messageFromOrcs();
		orcMessage.print();

		System.out.println("\nMessage from the elves: ");

		LetterComposite elfMessage = new Messenger().messageFromElves();
		elfMessage.print();
	}

}
