package play.learn.java.design.front_controller;

public class FrontController {
	public void handleRequest(String request) {
		Command command = getCommand(request);
		command.process();
	}

	private Command getCommand(String request) {
		Class<?> commandClass = getCommandClass(request);
		try {
			return (Command) commandClass.newInstance();
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
	}

	private static Class<?> getCommandClass(String request) {
		Class<?> result;
		try {
			result = Class.forName("play.learn.java.design.front_controller." + request + "Command");
		} catch (ClassNotFoundException e) {
			result = UnknownCommand.class;
		}
		return result;
	}
}
