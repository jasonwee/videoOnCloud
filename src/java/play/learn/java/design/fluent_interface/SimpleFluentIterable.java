package play.learn.java.design.fluent_interface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class SimpleFluentIterable<E> implements FluentIterable<E> {

	private final Iterable<E> iterable;

	/**
	 * This constructor creates a copy of a given iterable's contents.
	 * 
	 * @param iterable
	 *            the iterable this interface copies to work on.
	 */
	protected SimpleFluentIterable(Iterable<E> iterable) {
		this.iterable = iterable;
	}

	/**
	 * Filters the contents of Iterable using the given predicate, leaving only the
	 * ones which satisfy the predicate.
	 * 
	 * @param predicate
	 *            the condition to test with for the filtering. If the test is
	 *            negative, the tested object is removed by the iterator.
	 * @return the same FluentIterable with a filtered collection
	 */
	@Override
	public final FluentIterable<E> filter(Predicate<? super E> predicate) {
		Iterator<E> iterator = iterator();
		while (iterator.hasNext()) {
			E nextElement = iterator.next();
			if (!predicate.test(nextElement)) {
				iterator.remove();
			}
		}
		return this;
	}

	/**
	 * Can be used to collect objects from the Iterable. Is a terminating operation.
	 * 
	 * @return an option of the first object of the Iterable
	 */
	@Override
	public final Optional<E> first() {
		Iterator<E> resultIterator = first(1).iterator();
		return resultIterator.hasNext() ? Optional.of(resultIterator.next()) : Optional.empty();
	}

	/**
	 * Can be used to collect objects from the Iterable. Is a terminating operation.
	 * 
	 * @param count
	 *            defines the number of objects to return
	 * @return the same FluentIterable with a collection decimated to a maximum of
	 *         'count' first objects.
	 */
	@Override
	public final FluentIterable<E> first(int count) {
		Iterator<E> iterator = iterator();
		int currentCount = 0;
		while (iterator.hasNext()) {
			iterator.next();
			if (currentCount >= count) {
				iterator.remove();
			}
			currentCount++;
		}
		return this;
	}

	/**
	 * Can be used to collect objects from the Iterable. Is a terminating operation.
	 * 
	 * @return an option of the last object of the Iterable
	 */
	@Override
	public final Optional<E> last() {
		List<E> list = last(1).asList();
		if (list.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(list.get(0));
	}

	/**
	 * Can be used to collect objects from the Iterable. Is a terminating operation.
	 * 
	 * @param count
	 *            defines the number of objects to return
	 * @return the same FluentIterable with a collection decimated to a maximum of
	 *         'count' last objects
	 */
	@Override
	public final FluentIterable<E> last(int count) {
		int remainingElementsCount = getRemainingElementsCount();
		Iterator<E> iterator = iterator();
		int currentIndex = 0;
		while (iterator.hasNext()) {
			iterator.next();
			if (currentIndex < remainingElementsCount - count) {
				iterator.remove();
			}
			currentIndex++;
		}

		return this;
	}

	/**
	 * Transforms this FluentIterable into a new one containing objects of the type
	 * T.
	 * 
	 * @param function
	 *            a function that transforms an instance of E into an instance of T
	 * @param <T>
	 *            the target type of the transformation
	 * @return a new FluentIterable of the new type
	 */
	@Override
	public final <T> FluentIterable<T> map(Function<? super E, T> function) {
		List<T> temporaryList = new ArrayList<>();
		Iterator<E> iterator = iterator();
		while (iterator.hasNext()) {
			temporaryList.add(function.apply(iterator.next()));
		}
		return from(temporaryList);
	}

	/**
	 * Collects all remaining objects of this Iterable into a list.
	 * 
	 * @return a list with all remaining objects of this Iterable
	 */
	@Override
	public List<E> asList() {
		return toList(iterable.iterator());
	}

	/**
	 * @return a FluentIterable from a given iterable. Calls the
	 *         SimpleFluentIterable constructor.
	 */
	public static <E> FluentIterable<E> from(Iterable<E> iterable) {
		return new SimpleFluentIterable<>(iterable);
	}

	public static <E> FluentIterable<E> fromCopyOf(Iterable<E> iterable) {
		List<E> copy = FluentIterable.copyToList(iterable);
		return new SimpleFluentIterable<>(copy);
	}

	@Override
	public Iterator<E> iterator() {
		return iterable.iterator();
	}

	@Override
	public void forEach(Consumer<? super E> action) {
		iterable.forEach(action);
	}

	@Override
	public Spliterator<E> spliterator() {
		return iterable.spliterator();
	}

	/**
	 * @return the count of remaining objects of the current Iterable
	 */
	public final int getRemainingElementsCount() {
		int counter = 0;
		Iterator<E> iterator = iterator();
		while (iterator.hasNext()) {
			iterator.next();
			counter++;
		}
		return counter;
	}

	/**
	 * Collects the remaining objects of the given iterator into a List.
	 * 
	 * @return a new List with the remaining objects.
	 */
	public static <E> List<E> toList(Iterator<E> iterator) {
		List<E> copy = new ArrayList<>();
		while (iterator.hasNext()) {
			copy.add(iterator.next());
		}
		return copy;
	}

}