package play.learn.java.design.abstract_document;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// https://java-design-patterns.com/patterns/abstract-document/
public class AbstractDocumentDemo {
	/**
	 * Executes the App
	 */
	public AbstractDocumentDemo() {
		System.out.println("Constructing parts and car");

		Map<String, Object> carProperties = new HashMap<>();
		carProperties.put(HasModel.PROPERTY, "300SL");
		carProperties.put(HasPrice.PROPERTY, 10000L);

		Map<String, Object> wheelProperties = new HashMap<>();
		wheelProperties.put(HasType.PROPERTY, "wheel");
		wheelProperties.put(HasModel.PROPERTY, "15C");
		wheelProperties.put(HasPrice.PROPERTY, 100L);

		Map<String, Object> doorProperties = new HashMap<>();
		doorProperties.put(HasType.PROPERTY, "door");
		doorProperties.put(HasModel.PROPERTY, "Lambo");
		doorProperties.put(HasPrice.PROPERTY, 300L);

		carProperties.put(HasParts.PROPERTY, Arrays.asList(wheelProperties, doorProperties));

		Car car = new Car(carProperties);

		System.out.println("Here is our car:");
		System.out.printf("-> model: %s", car.getModel().get());
		System.out.printf("-> price: %s", car.getPrice().get());
		System.out.println("-> parts: ");
		car.getParts()
				.forEach(p -> System.out.printf("\t%s/%s/%s", p.getType().get(), p.getModel().get(), p.getPrice().get()));
	}

	/**
	 * Program entry point
	 *
	 * @param args
	 *            command line args
	 */
	public static void main(String[] args) {
		new AbstractDocumentDemo();
	}
}
