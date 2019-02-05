package play.learn.java.design.poison_pill;

import java.util.Map;


public interface Message {
	Message POISON_PILL = new Message() {

		@Override
		public void addHeader(Headers header, String value) {
			throw poison();
		}

		@Override
		public String getHeader(Headers header) {
			throw poison();
		}

		@Override
		public Map<Headers, String> getHeaders() {
			throw poison();
		}

		@Override
		public void setBody(String body) {
			throw poison();
		}

		@Override
		public String getBody() {
			throw poison();
		}

		private RuntimeException poison() {
			return new UnsupportedOperationException("Poison");
		}

	};

	/**
	 * Enumeration of Type of Headers
	 */
	public enum Headers {
		DATE, SENDER
	}

	void addHeader(Headers header, String value);

	String getHeader(Headers header);

	Map<Headers, String> getHeaders();

	void setBody(String body);

	String getBody();
}
