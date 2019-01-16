package play.learn.java.design.monostate;

public class Server {

	public final String host;
	public final int port;
	public final int id;

	/**
	 * Constructor
	 */
	public Server(String host, int port, int id) {
		this.host = host;
		this.port = port;
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public void serve(Request request) {
		System.out.format("Server ID %s associated to host : %s and port %s. Processed request with value %s", id, host,
				port, request.value);
	}

}
