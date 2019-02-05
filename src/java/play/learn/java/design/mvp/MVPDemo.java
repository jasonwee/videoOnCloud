package play.learn.java.design.mvp;

public class MVPDemo {
	public static void main(String[] args) {
		FileLoader loader = new FileLoader();
		FileSelectorJFrame jFrame = new FileSelectorJFrame();
		FileSelectorPresenter presenter = new FileSelectorPresenter(jFrame);
		presenter.setLoader(loader);
		presenter.start();
	}
}
