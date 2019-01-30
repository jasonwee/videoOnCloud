package play.learn.java.design.event_driven_architecture;

// https://java-design-patterns.com/patterns/event-driven-architecture/
public class EventDrivenArchitectureDemo {

	public static void main(String[] args) {

		EventDispatcher dispatcher = new EventDispatcher();
		dispatcher.registerHandler(UserCreatedEvent.class, new UserCreatedEventHandler());
		dispatcher.registerHandler(UserUpdatedEvent.class, new UserUpdatedEventHandler());

		User user = new User("jasonwee");
		dispatcher.dispatch(new UserCreatedEvent(user));
		dispatcher.dispatch(new UserUpdatedEvent(user));
	}
}
