package play.learn.java.design.object_mother;

public class King implements Royalty {

	boolean isDrunk = false;
	boolean isHappy = false;

	@Override
	public void makeDrunk() {
		isDrunk = true;
	}

	@Override
	public void makeSober() {
		isDrunk = false;
	}

	@Override
	public void makeHappy() {
		isHappy = true;
	}

	@Override
	public void makeUnhappy() {
		isHappy = false;
	}

	public boolean isHappy() {
		return isHappy;
	}

	/**
	 * Method to flirt to a queen.
	 * 
	 * @param queen
	 *            Queen which should be flirted.
	 */
	public void flirt(Queen queen) {
		boolean flirtStatus = queen.getFlirted(this);
		if (!flirtStatus) {
			this.makeUnhappy();
		} else {
			this.makeHappy();
		}

	}

}
