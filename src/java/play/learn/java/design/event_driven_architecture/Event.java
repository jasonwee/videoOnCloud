package play.learn.java.design.event_driven_architecture;

public interface Event {
	/**
	 * Returns the message type as a {@link Class} object. In this example the
	 * message type is used to handle events by their type.
	 * 
	 * @return the message type as a {@link Class}.
	 */
	Class<? extends Event> getType();
}
