package play.learn.java.design.iterator;

import java.util.ArrayList;
import java.util.List;

public class TreasureChest {
	
	 private List<Item> items;

	  /**
	   * Constructor
	   */
	  public TreasureChest() {
	    items = new ArrayList<>();
	    items.add(new Item(ItemType.POTION, "Potion of courage"));
	    items.add(new Item(ItemType.RING, "Ring of shadows"));
	    items.add(new Item(ItemType.POTION, "Potion of wisdom"));
	    items.add(new Item(ItemType.POTION, "Potion of blood"));
	    items.add(new Item(ItemType.WEAPON, "Sword of silver +1"));
	    items.add(new Item(ItemType.POTION, "Potion of rust"));
	    items.add(new Item(ItemType.POTION, "Potion of healing"));
	    items.add(new Item(ItemType.RING, "Ring of armor"));
	    items.add(new Item(ItemType.WEAPON, "Steel halberd"));
	    items.add(new Item(ItemType.WEAPON, "Dagger of poison"));
	  }

	  public Iterator<Item> iterator(ItemType itemType) {
	    return new TreasureChestItemIterator(this, itemType);
	  }

	  /**
	   * Get all items
	   */
	  public List<Item> getItems() {
	    return new ArrayList<>(items);
	  }


}
