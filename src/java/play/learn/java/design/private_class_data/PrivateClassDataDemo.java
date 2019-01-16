package play.learn.java.design.private_class_data;

// https://java-design-patterns.com/patterns/private-class-data/
public class PrivateClassDataDemo {

	public static void main(String[] args) {
		// stew is mutable
		Stew stew = new Stew(1, 2, 3, 4);
		stew.mix();
		stew.taste();
		stew.mix();

		// immutable stew protected with Private Class Data pattern
		ImmutableStew immutableStew = new ImmutableStew(2, 4, 3, 6);
		immutableStew.mix();
	}

}
