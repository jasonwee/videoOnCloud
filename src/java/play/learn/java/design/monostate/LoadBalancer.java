package play.learn.java.design.monostate;

import java.util.ArrayList;
import java.util.List;

public class LoadBalancer {

	private static final List<Server> SERVERS = new ArrayList<>();
	private static int lastServedId;

	static {
		int id = 0;
		for (int port : new int[] { 8080, 8081, 8082, 8083, 8084 }) {
			SERVERS.add(new Server("localhost", port, ++id));
		}
	}

	/**
	 * Add new server
	 */
	public final void addServer(Server server) {
		synchronized (SERVERS) {
			SERVERS.add(server);
		}

	}

	public final int getNoOfServers() {
		return SERVERS.size();
	}

	public int getLastServedId() {
		return lastServedId;
	}

	/**
	 * Handle request
	 */
	public synchronized void serverRequest(Request request) {
		if (lastServedId >= SERVERS.size()) {
			lastServedId = 0;
		}
		Server server = SERVERS.get(lastServedId++);
		server.serve(request);
	}

}
