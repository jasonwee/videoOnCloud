package play.learn.java.design.queue_based_load_leveling;

public class Message {
	private final String msg;

	// Parameter constructor.
	public Message(String msg) {
		this.msg = msg;
	}

	// Get Method for attribute msg.
	public String getMsg() {
		return msg;
	}

	@Override
	public String toString() {
		return msg;
	}
}
