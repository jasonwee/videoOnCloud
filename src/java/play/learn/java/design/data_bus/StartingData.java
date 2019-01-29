package play.learn.java.design.data_bus;

import java.time.LocalDateTime;

public class StartingData extends AbstractDataType {

	private final LocalDateTime when;

	public StartingData(LocalDateTime when) {
		this.when = when;
	}

	public LocalDateTime getWhen() {
		return when;
	}

	public static DataType of(final LocalDateTime when) {
		return new StartingData(when);
	}
}
