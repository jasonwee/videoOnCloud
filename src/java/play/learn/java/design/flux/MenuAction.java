package play.learn.java.design.flux;

public class MenuAction extends Action {

	private MenuItem menuItem;

	public MenuAction(MenuItem menuItem) {
		super(ActionType.MENU_ITEM_SELECTED);
		this.menuItem = menuItem;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}
}
