package play.learn.java.design.chain_of_responsibility;

public class OrcCommander extends RequestHandler {

	public OrcCommander(RequestHandler handler) {
		super(handler);
	}

	@Override
	public void handleRequest(Request req) {
		if (req.getRequestType().equals(RequestType.DEFEND_CASTLE)) {
			printHandling(req);
			req.markHandled();
		} else {
			super.handleRequest(req);
		}
	}

	@Override
	public String toString() {
		return "Orc commander";
	}

}
