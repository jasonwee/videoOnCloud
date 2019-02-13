package play.learn.java.design.twin;

public abstract class GameItem {

	/**
	 * Template method, do some common logic before draw
	 */
	public void draw() {
		System.out.println("draw");
		doDraw();
	}

	public abstract void doDraw();

	public abstract void click();
}
