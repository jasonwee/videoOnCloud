package play.learn.java.design.reactor;

import java.nio.channels.SelectionKey;


public interface ChannelHandler {
	  void handleChannelRead(AbstractNioChannel channel, Object readObject, SelectionKey key);

}
