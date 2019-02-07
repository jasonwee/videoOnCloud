package play.learn.java.design.queue_based_load_leveling;

public interface Task {
	void submit(Message msg);
}
