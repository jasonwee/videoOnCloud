package play.learn.java.design.object_mother;

public final class RoyaltyObjectMother {

	/**
	 * Method to create a sober and unhappy king. The standard parameters are set.
	 * 
	 * @return An instance of {@link com.iluwatar.objectmother.King} with the
	 *         standard properties.
	 */
	public static King createSoberUnhappyKing() {
		return new King();
	}

	/**
	 * Method of the object mother to create a drunk king.
	 * 
	 * @return A drunk {@link com.iluwatar.objectmother.King}.
	 */
	public static King createDrunkKing() {
		King king = new King();
		king.makeDrunk();
		return king;
	}

	/**
	 * Method to create a happy king.
	 * 
	 * @return A happy {@link com.iluwatar.objectmother.King}.
	 */
	public static King createHappyKing() {
		King king = new King();
		king.makeHappy();
		return king;
	}

	/**
	 * Method to create a happy and drunk king.
	 * 
	 * @return A drunk and happy {@link com.iluwatar.objectmother.King}.
	 */
	public static King createHappyDrunkKing() {
		King king = new King();
		king.makeHappy();
		king.makeDrunk();
		return king;
	}

	/**
	 * Method to create a flirty queen.
	 * 
	 * @return A flirty {@link com.iluwatar.objectmother.Queen}.
	 */
	public static Queen createFlirtyQueen() {
		Queen queen = new Queen();
		queen.setFlirtiness(true);
		return queen;
	}

	/**
	 * Method to create a not flirty queen.
	 * 
	 * @return A not flirty {@link com.iluwatar.objectmother.Queen}.
	 */
	public static Queen createNotFlirtyQueen() {
		return new Queen();
	}

}
