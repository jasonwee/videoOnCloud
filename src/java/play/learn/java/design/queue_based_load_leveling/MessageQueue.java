package play.learn.java.design.queue_based_load_leveling;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class MessageQueue {

	private final BlockingQueue<Message> blkQueue;

	// Default constructor when called creates Blocking Queue object.
	public MessageQueue() {
		this.blkQueue = new ArrayBlockingQueue<Message>(1024);
	}

	/**
	 * All the TaskGenerator threads will call this method to insert the Messages in
	 * to the Blocking Queue.
	 */
	public void submitMsg(Message msg) {
		try {
			if (null != msg) {
				blkQueue.add(msg);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * All the messages will be retrieved by the ServiceExecutor by calling this
	 * method and process them. Retrieves and removes the head of this queue, or
	 * returns null if this queue is empty.
	 */
	public Message retrieveMsg() {
		Message retrievedMsg = null;
		try {
			retrievedMsg = blkQueue.poll();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return retrievedMsg;
	}
}
