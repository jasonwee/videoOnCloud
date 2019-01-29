package play.learn.java.design.data_bus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageCollectorMember implements Member {

	private final String name;

	private List<String> messages = new ArrayList<>();

	public MessageCollectorMember(String name) {
		this.name = name;
	}

	@Override
	public void accept(final DataType data) {
		if (data instanceof MessageData) {
			handleEvent((MessageData) data);
		}
	}

	private void handleEvent(MessageData data) {
		System.out.println(String.format("%s sees message %s", name, data.getMessage()));
		messages.add(data.getMessage());
	}

	public List<String> getMessages() {
		return Collections.unmodifiableList(messages);
	}

}
