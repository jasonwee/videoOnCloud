package play.learn.java.design.mute_idiom;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;

// https://java-design-patterns.com/patterns/mute-idiom/
public class MuteIdiomDemo {

	public static void main(String[] args) throws Exception {

		useOfLoggedMute();

		useOfMute();
	}

	/*
	 * Typically used when the API declares some exception but cannot do so. Usually
	 * a signature mistake.In this example out is not supposed to throw exception as
	 * it is a ByteArrayOutputStream. So we utilize mute, which will throw
	 * AssertionError if unexpected exception occurs.
	 */
	private static void useOfMute() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Mute.mute(() -> out.write("Hello".getBytes()));
	}

	private static void useOfLoggedMute() throws SQLException {
		Resource resource = null;
		try {
			resource = acquireResource();
			utilizeResource(resource);
		} finally {
			closeResource(resource);
		}
	}

	/*
	 * All we can do while failed close of a resource is to log it.
	 */
	private static void closeResource(Resource resource) {
		Mute.loggedMute(() -> resource.close());
	}

	private static void utilizeResource(Resource resource) throws SQLException {
		System.out.format("Utilizing acquired resource: %s", resource);
	}

	private static Resource acquireResource() throws SQLException {
		return new Resource() {

			@Override
			public void close() throws IOException {
				throw new IOException("Error in closing resource: " + this);
			}
		};
	}

}
