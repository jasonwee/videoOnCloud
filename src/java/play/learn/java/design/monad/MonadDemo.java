package play.learn.java.design.monad;

import java.util.Objects;

// https://java-design-patterns.com/patterns/monad/
public class MonadDemo {
	public static void main(String[] args) {
		User user = new User("user", 24, Sex.FEMALE, "foobar.com");
		System.out.println(Validator.of(user).validate(User::getName, Objects::nonNull, "name is null")
				.validate(User::getName, name -> !name.isEmpty(), "name is empty")
				.validate(User::getEmail, email -> !email.contains("@"), "email doesn't containt '@'")
				.validate(User::getAge, age -> age > 20 && age < 30, "age isn't between...").get().toString());
	}
}
