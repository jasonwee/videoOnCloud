package play.learn.java.design.event_driven_architecture;

public class AbstractEvent implements Event {
	/**
	 * Returns the event type as a {@link Class} object In this example, this method
	 * is used by the {@link EventDispatcher} to dispatch events depending on their
	 * type.
	 *
	 * @return the AbstractEvent type as a {@link Class}.
	 */
	public Class<? extends Event> getType() {
		return getClass();
	}
}
