package play.learn.java.design.hexagonal;

import java.util.Map;
import java.util.Optional;

public interface LotteryTicketRepository {
	/**
	 * Find lottery ticket by id
	 */
	Optional<LotteryTicket> findById(LotteryTicketId id);

	/**
	 * Save lottery ticket
	 */
	Optional<LotteryTicketId> save(LotteryTicket ticket);

	/**
	 * Get all lottery tickets
	 */
	Map<LotteryTicketId, LotteryTicket> findAll();

	/**
	 * Delete all lottery tickets
	 */
	void deleteAll();
}
