package play.learn.java.design.iterator;

import static play.learn.java.design.iterator.ItemType.ANY;
import static play.learn.java.design.iterator.ItemType.POTION;
import static play.learn.java.design.iterator.ItemType.RING;
import static play.learn.java.design.iterator.ItemType.WEAPON;

// https://java-design-patterns.com/patterns/iterator/
public class IteratorDemo {

	private static final TreasureChest TREASURE_CHEST = new TreasureChest();

	private static void demonstrateTreasureChestIteratorForType(ItemType itemType) {
		System.out.println("------------------------");
		System.out.println("Item Iterator for ItemType " + itemType + ": ");
		Iterator<Item> itemIterator = TREASURE_CHEST.iterator(itemType);
		while (itemIterator.hasNext()) {
			System.out.println(itemIterator.next().toString());
		}
	}

	private static void demonstrateBstIterator() {
		System.out.println("------------------------");
		System.out.println("BST Iterator: ");
		TreeNode<Integer> root = buildIntegerBst();
		BstIterator bstIterator = new BstIterator<>(root);
		while (bstIterator.hasNext()) {
			System.out.println("Next node: " + bstIterator.next().getVal());
		}
	}

	private static TreeNode<Integer> buildIntegerBst() {
		TreeNode<Integer> root = new TreeNode<>(8);

		root.insert(3);
		root.insert(10);
		root.insert(1);
		root.insert(6);
		root.insert(14);
		root.insert(4);
		root.insert(7);
		root.insert(13);

		return root;
	}

	/**
	 * Program entry point
	 *
	 * @param args
	 *            command line args
	 */
	public static void main(String[] args) {
		demonstrateTreasureChestIteratorForType(RING);
		demonstrateTreasureChestIteratorForType(POTION);
		demonstrateTreasureChestIteratorForType(WEAPON);
		demonstrateTreasureChestIteratorForType(ANY);

		demonstrateBstIterator();
	}

}
