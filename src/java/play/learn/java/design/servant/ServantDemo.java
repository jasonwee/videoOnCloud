package play.learn.java.design.servant;

import java.util.ArrayList;
import java.util.List;

// https://java-design-patterns.com/patterns/servant/
public class ServantDemo {
	static Servant jenkins = new Servant("Jenkins");
	static Servant travis = new Servant("Travis");

	/**
	 * Program entry point
	 */
	public static void main(String[] args) {
		scenario(jenkins, 1);
		scenario(travis, 0);
	}

	/**
	 * Can add a List with enum Actions for variable scenarios
	 */
	public static void scenario(Servant servant, int compliment) {
		King k = new King();
		Queen q = new Queen();

		List<Royalty> guests = new ArrayList<>();
		guests.add(k);
		guests.add(q);

		// feed
		servant.feed(k);
		servant.feed(q);
		// serve drinks
		servant.giveWine(k);
		servant.giveWine(q);
		// compliment
		servant.giveCompliments(guests.get(compliment));

		// outcome of the night
		for (Royalty r : guests) {
			r.changeMood();
		}

		// check your luck
		if (servant.checkIfYouWillBeHanged(guests)) {
			System.out.printf("%s will live another day", servant.name);
		} else {
			System.out.printf("Poor %s. His days are numbered", servant.name);
		}
	}
}
