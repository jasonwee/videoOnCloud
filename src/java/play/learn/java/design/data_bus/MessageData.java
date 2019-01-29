package play.learn.java.design.data_bus;

public class MessageData extends AbstractDataType {
	private final String message;

	public MessageData(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public static DataType of(final String message) {
		return new MessageData(message);
	}
}
