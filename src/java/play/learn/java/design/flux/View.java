package play.learn.java.design.flux;

public interface View {
	void storeChanged(Store store);

	void render();
}
