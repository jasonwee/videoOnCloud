package play.learn.java.design.abstract_document;

import java.util.Optional;


public interface HasType extends Document {

	String PROPERTY = "type";

	default Optional<String> getType() {
		return Optional.ofNullable((String) get(PROPERTY));
	}

}
