package play.learn.java.design.data_bus;

import java.time.LocalDateTime;

public class StatusMember implements Member {

	private final int id;

	private LocalDateTime started;

	private LocalDateTime stopped;

	public StatusMember(int id) {
		this.id = id;
	}

	@Override
	public void accept(final DataType data) {
		if (data instanceof StartingData) {
			handleEvent((StartingData) data);
		} else if (data instanceof StoppingData) {
			handleEvent((StoppingData) data);
		}
	}

	private void handleEvent(StartingData data) {
		started = data.getWhen();
		System.out.println(String.format("Receiver #%d sees application started at %s", id, started));
	}

	private void handleEvent(StoppingData data) {
		stopped = data.getWhen();
		System.out.println(String.format("Receiver #%d sees application stopping at %s", id, stopped));
		System.out.println(String.format("Receiver #%d sending goodbye message", id));
		data.getDataBus().publish(MessageData.of(String.format("Goodbye cruel world from #%d!", id)));
	}

	public LocalDateTime getStarted() {
		return started;
	}

	public LocalDateTime getStopped() {
		return stopped;
	}

}
