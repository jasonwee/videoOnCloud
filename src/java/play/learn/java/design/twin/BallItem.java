package play.learn.java.design.twin;

public class BallItem extends GameItem {
	  private boolean isSuspended;

	  private BallThread twin;

	  public void setTwin(BallThread twin) {
	    this.twin = twin;
	  }

	  @Override
	  public void doDraw() {

	    System.out.println("doDraw");
	  }

	  public void move() {
	    System.out.println("move");
	  }

	  @Override
	  public void click() {

	    isSuspended = !isSuspended;

	    if (isSuspended) {
	      twin.suspendMe();
	    } else {
	      twin.resumeMe();
	    }
	  }
}
