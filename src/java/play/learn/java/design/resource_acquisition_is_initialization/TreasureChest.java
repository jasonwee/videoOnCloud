package play.learn.java.design.resource_acquisition_is_initialization;

import java.io.Closeable;
import java.io.IOException;

public class TreasureChest implements Closeable {

	public TreasureChest() {
		System.out.println("Treasure chest opens.");
	}

	@Override
	public void close() throws IOException {
		System.out.println("Treasure chest closes.");
	}

}
