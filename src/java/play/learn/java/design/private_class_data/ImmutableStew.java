package play.learn.java.design.private_class_data;

public class ImmutableStew {

	private StewData data;

	public ImmutableStew(int numPotatoes, int numCarrots, int numMeat, int numPeppers) {
		data = new StewData(numPotatoes, numCarrots, numMeat, numPeppers);
	}

	/**
	 * Mix the stew
	 */
	public void mix() {
		System.out.format("Mixing the immutable stew we find: %s potatoes, %s carrots, %s meat and %s peppers",
				data.getNumPotatoes(), data.getNumCarrots(), data.getNumMeat(), data.getNumPeppers());
	}
}
