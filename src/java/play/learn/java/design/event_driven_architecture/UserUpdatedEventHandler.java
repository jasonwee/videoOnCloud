package play.learn.java.design.event_driven_architecture;

public class UserUpdatedEventHandler implements Handler<UserUpdatedEvent> {

	@Override
	public void onEvent(UserUpdatedEvent event) {
		System.out.printf("User '%s' has been Updated!", event.getUser().getUsername());
	}

}
