package play.learn.java.design.flux;

public class ContentView implements View {

	private Content content = Content.PRODUCTS;

	@Override
	public void storeChanged(Store store) {
		ContentStore contentStore = (ContentStore) store;
		content = contentStore.getContent();
		render();
	}

	@Override
	public void render() {
		System.out.println(content.toString());
	}

}
