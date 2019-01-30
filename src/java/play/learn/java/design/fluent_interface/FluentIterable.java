package play.learn.java.design.fluent_interface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public interface FluentIterable<E> extends Iterable<E> {
	/**
	 * Filters the contents of Iterable using the given predicate, leaving only the
	 * ones which satisfy the predicate.
	 * 
	 * @param predicate
	 *            the condition to test with for the filtering. If the test is
	 *            negative, the tested object is removed by the iterator.
	 * @return a filtered FluentIterable
	 */
	FluentIterable<E> filter(Predicate<? super E> predicate);

	/**
	 * Returns an Optional containing the first element of this iterable if present,
	 * else returns Optional.empty().
	 * 
	 * @return the first element after the iteration is evaluated
	 */
	Optional<E> first();

	/**
	 * Evaluates the iteration and leaves only the count first elements.
	 * 
	 * @return the first count elements as an Iterable
	 */
	FluentIterable<E> first(int count);

	/**
	 * Evaluates the iteration and returns the last element. This is a terminating
	 * operation.
	 * 
	 * @return the last element after the iteration is evaluated
	 */
	Optional<E> last();

	/**
	 * Evaluates the iteration and leaves only the count last elements.
	 * 
	 * @return the last counts elements as an Iterable
	 */
	FluentIterable<E> last(int count);

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
	<T> FluentIterable<T> map(Function<? super E, T> function);

	/**
	 * Returns the contents of this Iterable as a List.
	 * 
	 * @return a List representation of this Iterable
	 */
	List<E> asList();

	/**
	 * Utility method that iterates over iterable and adds the contents to a list.
	 * 
	 * @param iterable
	 *            the iterable to collect
	 * @param <E>
	 *            the type of the objects to iterate
	 * @return a list with all objects of the given iterator
	 */
	static <E> List<E> copyToList(Iterable<E> iterable) {
		List<E> copy = new ArrayList<>();
		Iterator<E> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			copy.add(iterator.next());
		}
		return copy;
	}
}
