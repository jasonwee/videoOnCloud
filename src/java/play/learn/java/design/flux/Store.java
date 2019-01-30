package play.learn.java.design.flux;

import java.util.LinkedList;
import java.util.List;

public abstract class Store {
	private List<View> views = new LinkedList<>();

	public abstract void onAction(Action action);

	public void registerView(View view) {
		views.add(view);
	}

	protected void notifyChange() {
		views.stream().forEach(view -> view.storeChanged(this));
	}
}
