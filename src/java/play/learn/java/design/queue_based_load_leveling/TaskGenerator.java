package play.learn.java.design.queue_based_load_leveling;

public class TaskGenerator implements Task, Runnable {

	// MessageQueue reference using which we will submit our messages.
	private final MessageQueue msgQueue;

	// Total message count that a TaskGenerator will submit.
	private final int msgCount;

	// Parameterized constructor.
	public TaskGenerator(MessageQueue msgQueue, int msgCount) {
		this.msgQueue = msgQueue;
		this.msgCount = msgCount;
	}

	/**
	 * Submit messages to the Blocking Queue.
	 */
	public void submit(Message msg) {
		try {
			this.msgQueue.submitMsg(msg);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Each TaskGenerator thread will submit all the messages to the Queue. After
	 * every message submission TaskGenerator thread will sleep for 1 second.
	 */
	public void run() {

		int count = this.msgCount;

		try {
			while (count > 0) {
				String statusMsg = "Message-" + count + " submitted by " + Thread.currentThread().getName();
				this.submit(new Message(statusMsg));

				System.out.println(statusMsg);

				// reduce the message count.
				count--;

				// Make the current thread to sleep after every Message submission.
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
