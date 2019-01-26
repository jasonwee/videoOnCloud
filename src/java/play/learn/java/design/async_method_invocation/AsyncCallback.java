package play.learn.java.design.async_method_invocation;

import java.util.Optional;

public interface AsyncCallback<T> {
	void onComplete(T value, Optional<Exception> ex);

}
