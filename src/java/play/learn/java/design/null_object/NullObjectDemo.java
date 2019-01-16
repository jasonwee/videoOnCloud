package play.learn.java.design.null_object;

// https://java-design-patterns.com/patterns/null-object/
public class NullObjectDemo {

	public static void main(String[] args) {

		Node root = new NodeImpl("1",
				new NodeImpl("11", new NodeImpl("111", NullNode.getInstance(), NullNode.getInstance()),
						NullNode.getInstance()),
				new NodeImpl("12", NullNode.getInstance(),
						new NodeImpl("122", NullNode.getInstance(), NullNode.getInstance())));

		root.walk();
	}

}
