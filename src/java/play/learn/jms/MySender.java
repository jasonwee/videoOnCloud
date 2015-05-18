package play.learn.jms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class MySender {

	public static void main(String[] args) {
		try {
			// create and start connection
			InitialContext ctx = new InitialContext();
			QueueConnectionFactory factory = (QueueConnectionFactory)ctx.lookup("myQueueConnectionFactory");
			QueueConnection con = factory.createQueueConnection();
			con.start();
			
			// create queue session
			QueueSession qSession = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			
			// get the queue object
			Queue t = (Queue) ctx.lookup("myQueue");
			
			// create queuesender object
			QueueSender sender = qSession.createSender(t);
			
			// create TextMessage object
			TextMessage msg = qSession.createTextMessage();
			
			// write message
			BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
			
			while (true) {
				System.out.println("Enter msg, end to terminate:");
				String s = b.readLine();
				if (s.endsWith("end"))
					break;
				msg.setText(s);
				
				// send message
				sender.send(msg);
				System.out.println("Message successfully sent.");
			}
			
			//connection close
			con.close();
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
