package play.learn.java.design.flux;

// https://java-design-patterns.com/patterns/flux/
public class FluxDemo {

	public static void main(String[] args) {

		// initialize and wire the system
		MenuStore menuStore = new MenuStore();
		Dispatcher.getInstance().registerStore(menuStore);
		ContentStore contentStore = new ContentStore();
		Dispatcher.getInstance().registerStore(contentStore);
		MenuView menuView = new MenuView();
		menuStore.registerView(menuView);
		ContentView contentView = new ContentView();
		contentStore.registerView(contentView);

		// render initial view
		menuView.render();
		contentView.render();

		// user clicks another menu item
		// this triggers action dispatching and eventually causes views to render with
		// new content
		menuView.itemClicked(MenuItem.COMPANY);
	}

}
