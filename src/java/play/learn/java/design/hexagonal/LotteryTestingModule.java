package play.learn.java.design.hexagonal;

import com.google.inject.AbstractModule;

public class LotteryTestingModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(LotteryTicketRepository.class).to(InMemoryTicketRepository.class);
		bind(LotteryEventLog.class).to(StdOutEventLog.class);
		bind(WireTransfers.class).to(InMemoryBank.class);
	}
}
