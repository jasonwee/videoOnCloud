package play.learn.java.design.object_pool;

public class OliphauntPool extends ObjectPool<Oliphaunt> {

	@Override
	protected Oliphaunt create() {
	    return new Oliphaunt();
	}

}
