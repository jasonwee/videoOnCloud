package play.learn.java.design.callback;

// https://github.com/iluwatar/java-design-patterns/blob/master/callback/src/main/java/com/iluwatar/callback/App.java
public class CallbackDemo {

	public static void main(String[] args) {
		Task task = new SimpleTask();
		Callback callback = () -> System.out.println("I'm done now.");
		task.executeWith(callback);
	}

}
