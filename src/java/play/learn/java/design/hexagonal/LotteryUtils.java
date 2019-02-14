package play.learn.java.design.hexagonal;

import java.util.Optional;


public class LotteryUtils {
	private LotteryUtils() {
	}

	/**
	 * Checks if lottery ticket has won
	 */
	public static LotteryTicketCheckResult checkTicketForPrize(LotteryTicketRepository repository, LotteryTicketId id,
			LotteryNumbers winningNumbers) {
		Optional<LotteryTicket> optional = repository.findById(id);
		if (optional.isPresent()) {
			if (optional.get().getNumbers().equals(winningNumbers)) {
				return new LotteryTicketCheckResult(LotteryTicketCheckResult.CheckResult.WIN_PRIZE, 1000);
			} else {
				return new LotteryTicketCheckResult(LotteryTicketCheckResult.CheckResult.NO_PRIZE);
			}
		} else {
			return new LotteryTicketCheckResult(LotteryTicketCheckResult.CheckResult.TICKET_NOT_SUBMITTED);
		}
	}
}
