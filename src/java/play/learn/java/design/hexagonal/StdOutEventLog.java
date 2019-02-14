package play.learn.java.design.hexagonal;

public class StdOutEventLog implements LotteryEventLog {
	@Override
	public void ticketSubmitted(PlayerDetails details) {
		System.out.printf("Lottery ticket for %s was submitted. Bank account %s was charged for 3 credits.",
				details.getEmail(), details.getBankAccount());
	}

	@Override
	public void ticketDidNotWin(PlayerDetails details) {
		System.out.printf("Lottery ticket for %s was checked and unfortunately did not win this time.", details.getEmail());
	}

	@Override
	public void ticketWon(PlayerDetails details, int prizeAmount) {
		System.out.printf("Lottery ticket for %s has won! The bank account %s was deposited with %s credits.",
				details.getEmail(), details.getBankAccount(), prizeAmount);
	}

	@Override
	public void prizeError(PlayerDetails details, int prizeAmount) {
		System.out.printf("Lottery ticket for %s has won! Unfortunately the bank credit transfer of %s failed.",
				details.getEmail(), prizeAmount);
	}

	@Override
	public void ticketSubmitError(PlayerDetails details) {
		System.out.printf("Lottery ticket for %s could not be submitted because the credit transfer of 3 credits failed.",
				details.getEmail());
	}
}
