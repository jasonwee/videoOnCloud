package play.learn.java.design.flux;

public class MenuView implements View {
	private MenuItem selected = MenuItem.HOME;

	@Override
	public void storeChanged(Store store) {
		MenuStore menuStore = (MenuStore) store;
		selected = menuStore.getSelected();
		render();
	}

	@Override
	public void render() {
		for (MenuItem item : MenuItem.values()) {
			if (selected.equals(item)) {
				System.out.printf("* %s", item);
			} else {
				System.out.println(item.toString());
			}
		}
	}

	public void itemClicked(MenuItem item) {
		Dispatcher.getInstance().menuItemSelected(item);
	}
}
