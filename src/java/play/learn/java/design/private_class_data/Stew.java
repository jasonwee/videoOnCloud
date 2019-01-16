package play.learn.java.design.private_class_data;

public class Stew {

	private int numPotatoes;
	private int numCarrots;
	private int numMeat;
	private int numPeppers;

	/**
	 * Constructor
	 */
	public Stew(int numPotatoes, int numCarrots, int numMeat, int numPeppers) {
		this.numPotatoes = numPotatoes;
		this.numCarrots = numCarrots;
		this.numMeat = numMeat;
		this.numPeppers = numPeppers;
	}

	/**
	 * Mix the stew
	 */
	public void mix() {
		System.out.format("Mixing the stew we find: %s potatoes, %s carrots, %s meat and %s peppers", numPotatoes,
				numCarrots, numMeat, numPeppers);
	}

	/**
	 * Taste the stew
	 */
	public void taste() {
		System.out.println("Tasting the stew");
		if (numPotatoes > 0) {
			numPotatoes--;
		}
		if (numCarrots > 0) {
			numCarrots--;
		}
		if (numMeat > 0) {
			numMeat--;
		}
		if (numPeppers > 0) {
			numPeppers--;
		}
	}

}
