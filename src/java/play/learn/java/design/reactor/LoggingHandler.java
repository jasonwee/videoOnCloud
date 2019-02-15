package play.learn.java.design.reactor;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

import play.learn.java.design.reactor.NioDatagramChannel.DatagramPacket;;

public class LoggingHandler implements ChannelHandler {

	private static final byte[] ACK = "Data logged successfully".getBytes();

	/**
	 * Decodes the received data and logs it on standard console.
	 */
	@Override
	public void handleChannelRead(AbstractNioChannel channel, Object readObject, SelectionKey key) {
		/*
		 * As this handler is attached with both TCP and UDP channels we need to check
		 * whether the data received is a ByteBuffer (from TCP channel) or a
		 * DatagramPacket (from UDP channel).
		 */
		if (readObject instanceof ByteBuffer) {
			doLogging((ByteBuffer) readObject);
			sendReply(channel, key);
		} else if (readObject instanceof DatagramPacket) {
			DatagramPacket datagram = (DatagramPacket) readObject;
			doLogging(datagram.getData());
			sendReply(channel, datagram, key);
		} else {
			throw new IllegalStateException("Unknown data received");
		}
	}

	private static void sendReply(AbstractNioChannel channel, DatagramPacket incomingPacket, SelectionKey key) {
		/*
		 * Create a reply acknowledgement datagram packet setting the receiver to the
		 * sender of incoming message.
		 */
		DatagramPacket replyPacket = new DatagramPacket(ByteBuffer.wrap(ACK));
		replyPacket.setReceiver(incomingPacket.getSender());

		channel.write(replyPacket, key);
	}

	private static void sendReply(AbstractNioChannel channel, SelectionKey key) {
		ByteBuffer buffer = ByteBuffer.wrap(ACK);
		channel.write(buffer, key);
	}

	private static void doLogging(ByteBuffer data) {
		// assuming UTF-8 :(
		System.out.println(new String(data.array(), 0, data.limit()));
	}

}
