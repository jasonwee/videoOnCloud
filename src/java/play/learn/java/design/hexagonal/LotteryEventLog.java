package play.learn.java.design.hexagonal;

public interface LotteryEventLog {
	/**
	 * lottery ticket submitted
	 */
	void ticketSubmitted(PlayerDetails details);

	/**
	 * error submitting lottery ticket
	 */
	void ticketSubmitError(PlayerDetails details);

	/**
	 * lottery ticket did not win
	 */
	void ticketDidNotWin(PlayerDetails details);

	/**
	 * lottery ticket won
	 */
	void ticketWon(PlayerDetails details, int prizeAmount);

	/**
	 * error paying the prize
	 */
	void prizeError(PlayerDetails details, int prizeAmount);
}
