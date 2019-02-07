package play.learn.java.design.queue_based_load_leveling;

public class ServiceExecutor implements Runnable {

	private final MessageQueue msgQueue;

	public ServiceExecutor(MessageQueue msgQueue) {
		this.msgQueue = msgQueue;
	}

	/**
	 * The ServiceExecutor thread will retrieve each message and process it.
	 */
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				Message msg = msgQueue.retrieveMsg();

				if (null != msg) {
					System.out.println(msg.toString() + " is served.");
				} else {
					System.out.println("Service Executor: Waiting for Messages to serve .. ");
				}

				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
