package play.learn.java.design.hexagonal;

import com.google.inject.Inject;
//import com.iluwatar.hexagonal.banking.WireTransfers;
//import com.iluwatar.hexagonal.database.LotteryTicketRepository;
//import com.iluwatar.hexagonal.eventlog.LotteryEventLog;

import java.util.Map;

public class LotteryAdministration {
	private final LotteryTicketRepository repository;
	private final LotteryEventLog notifications;
	private final WireTransfers wireTransfers;

	/**
	 * Constructor
	 */
	@Inject
	public LotteryAdministration(LotteryTicketRepository repository, LotteryEventLog notifications,
			WireTransfers wireTransfers) {
		this.repository = repository;
		this.notifications = notifications;
		this.wireTransfers = wireTransfers;
	}

	/**
	 * Get all the lottery tickets submitted for lottery
	 */
	public Map<LotteryTicketId, LotteryTicket> getAllSubmittedTickets() {
		return repository.findAll();
	}

	/**
	 * Draw lottery numbers
	 */
	public LotteryNumbers performLottery() {
		LotteryNumbers numbers = LotteryNumbers.createRandom();
		Map<LotteryTicketId, LotteryTicket> tickets = getAllSubmittedTickets();
		for (LotteryTicketId id : tickets.keySet()) {
			LotteryTicketCheckResult result = LotteryUtils.checkTicketForPrize(repository, id, numbers);
			if (result.getResult().equals(LotteryTicketCheckResult.CheckResult.WIN_PRIZE)) {
				boolean transferred = wireTransfers.transferFunds(LotteryConstants.PRIZE_AMOUNT,
						LotteryConstants.SERVICE_BANK_ACCOUNT, tickets.get(id).getPlayerDetails().getBankAccount());
				if (transferred) {
					notifications.ticketWon(tickets.get(id).getPlayerDetails(), LotteryConstants.PRIZE_AMOUNT);
				} else {
					notifications.prizeError(tickets.get(id).getPlayerDetails(), LotteryConstants.PRIZE_AMOUNT);
				}
			} else if (result.getResult().equals(LotteryTicketCheckResult.CheckResult.NO_PRIZE)) {
				notifications.ticketDidNotWin(tickets.get(id).getPlayerDetails());
			}
		}
		return numbers;
	}

	/**
	 * Begin new lottery round
	 */
	public void resetLottery() {
		repository.deleteAll();
	}
}
