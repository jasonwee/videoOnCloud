package play.learn.java.design.data_bus;

import java.time.LocalDateTime;


public class StoppingData extends AbstractDataType {
	private final LocalDateTime when;

	public StoppingData(LocalDateTime when) {
		this.when = when;
	}

	public LocalDateTime getWhen() {
		return when;
	}

	public static DataType of(final LocalDateTime when) {
		return new StoppingData(when);
	}
}
