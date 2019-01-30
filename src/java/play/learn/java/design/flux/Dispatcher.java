package play.learn.java.design.flux;

import java.util.LinkedList;
import java.util.List;

public final class Dispatcher {

	private static Dispatcher instance = new Dispatcher();

	private List<Store> stores = new LinkedList<>();

	private Dispatcher() {
	}

	public static Dispatcher getInstance() {
		return instance;
	}

	public void registerStore(Store store) {
		stores.add(store);
	}

	/**
	 * Menu item selected handler
	 */
	public void menuItemSelected(MenuItem menuItem) {
		dispatchAction(new MenuAction(menuItem));
		switch (menuItem) {
		case HOME:
		case PRODUCTS:
		default:
			dispatchAction(new ContentAction(Content.PRODUCTS));
			break;
		case COMPANY:
			dispatchAction(new ContentAction(Content.COMPANY));
			break;
		}
	}

	private void dispatchAction(Action action) {
		stores.stream().forEach(store -> store.onAction(action));
	}

}
