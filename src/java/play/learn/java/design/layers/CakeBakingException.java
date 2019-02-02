package play.learn.java.design.layers;

public class CakeBakingException extends Exception {
	private static final long serialVersionUID = 1L;

	public CakeBakingException() {
	}

	public CakeBakingException(String message) {
		super(message);
	}
}
