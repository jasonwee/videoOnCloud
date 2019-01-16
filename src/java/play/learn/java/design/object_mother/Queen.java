package play.learn.java.design.object_mother;

public class Queen implements Royalty {

	private boolean isDrunk = false;
	private boolean isHappy = false;
	private boolean isFlirty = false;

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

	public boolean isFlirty() {
		return isFlirty;
	}

	public void setFlirtiness(boolean flirtiness) {
		this.isFlirty = flirtiness;
	}

	/**
	 * Method which is called when the king is flirting to a queen.
	 * 
	 * @param king
	 *            King who initialized the flirt.
	 * @return A value which describes if the flirt was successful or not.
	 */
	public boolean getFlirted(King king) {
		if (this.isFlirty && king.isHappy && !king.isDrunk) {
			return true;
		}
		return false;
	}

}
