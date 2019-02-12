package play.learn.java.design.thread_pool;

public class Worker implements Runnable {

	private final Task task;

	public Worker(final Task task) {
		this.task = task;
	}

	@Override
	public void run() {
		System.out.printf("%s processing %s%n", Thread.currentThread().getName(), task.toString());
		try {
			Thread.sleep(task.getTimeMs());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
