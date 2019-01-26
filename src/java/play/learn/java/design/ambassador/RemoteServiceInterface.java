package play.learn.java.design.ambassador;

public interface RemoteServiceInterface {
	int FAILURE = -1;

	long doRemoteFunction(int value) throws Exception;
}
