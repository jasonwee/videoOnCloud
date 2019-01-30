package play.learn.java.design.event_driven_architecture;

public class UserCreatedEventHandler implements Handler<UserCreatedEvent> {
	@Override
	public void onEvent(UserCreatedEvent event) {
		System.out.printf("User '%s' has been Created!", event.getUser().getUsername());
	}

}
