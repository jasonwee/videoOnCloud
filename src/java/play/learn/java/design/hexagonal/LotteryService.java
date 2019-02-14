package play.learn.java.design.hexagonal;

import com.google.inject.Inject;

import java.util.Optional;

public class LotteryService {
	private final LotteryTicketRepository repository;
	private final LotteryEventLog notifications;
	private final WireTransfers wireTransfers;

	/**
	 * Constructor
	 */
	@Inject
	public LotteryService(LotteryTicketRepository repository, LotteryEventLog notifications,
			WireTransfers wireTransfers) {
		this.repository = repository;
		this.notifications = notifications;
		this.wireTransfers = wireTransfers;
	}

	/**
	 * Submit lottery ticket to participate in the lottery
	 */
	public Optional<LotteryTicketId> submitTicket(LotteryTicket ticket) {
		boolean result = wireTransfers.transferFunds(LotteryConstants.TICKET_PRIZE,
				ticket.getPlayerDetails().getBankAccount(), LotteryConstants.SERVICE_BANK_ACCOUNT);
		if (!result) {
			notifications.ticketSubmitError(ticket.getPlayerDetails());
			return Optional.empty();
		}
		Optional<LotteryTicketId> optional = repository.save(ticket);
		if (optional.isPresent()) {
			notifications.ticketSubmitted(ticket.getPlayerDetails());
		}
		return optional;
	}

	/**
	 * Check if lottery ticket has won
	 */
	public LotteryTicketCheckResult checkTicketForPrize(LotteryTicketId id, LotteryNumbers winningNumbers) {
		return LotteryUtils.checkTicketForPrize(repository, id, winningNumbers);
	}
}
