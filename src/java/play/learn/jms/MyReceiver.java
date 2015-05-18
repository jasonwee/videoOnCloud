package play.learn.jms;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * should be able to test in TomEE
 * 1. run receiver class
 * 2. run sender class. 
 * http://www.javatpoint.com/jms-tutorial
 *
 */
public class MyReceiver {

	public static void main(String[] args) {
		try {
			// create and start connection
			InitialContext ctx = new InitialContext();
			QueueConnectionFactory f = (QueueConnectionFactory) ctx.lookup("myQueueConnectionFactory");
			QueueConnection con = f.createQueueConnection();
			con.start();
			
			// create queue session
			QueueSession qSession = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			
			// get the queue object
			Queue t = (Queue) ctx.lookup("myQueue");
			
			// create queueReceiver
			QueueReceiver receiver = qSession.createReceiver(t);
			
			// create listener object
			MyListener listener = new MyListener();
			
			// register the listener object with receiver
			receiver.setMessageListener(listener);
			
			System.out.println("Receiver1 is ready, waiting for messages...");
			System.out.println("press Ctrl+c to shutdown...");
			
			while (true) {
				Thread.sleep(3000);
			}
			
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
