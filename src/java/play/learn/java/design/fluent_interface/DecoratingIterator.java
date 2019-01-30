package play.learn.java.design.fluent_interface;

import java.util.Iterator;

public abstract class DecoratingIterator<E> implements Iterator<E> {
	protected final Iterator<E> fromIterator;

	private E next;

	/**
	 * Creates an iterator that decorates the given iterator.
	 */
	public DecoratingIterator(Iterator<E> fromIterator) {
		this.fromIterator = fromIterator;
	}

	/**
	 * Precomputes and saves the next element of the Iterable. null is considered as
	 * end of data.
	 *
	 * @return true if a next element is available
	 */
	@Override
	public final boolean hasNext() {
		next = computeNext();
		return next != null;
	}

	/**
	 * Returns the next element of the Iterable.
	 *
	 * @return the next element of the Iterable, or null if not present.
	 */
	@Override
	public final E next() {
		if (next == null) {
			return fromIterator.next();
		} else {
			final E result = next;
			next = null;
			return result;
		}
	}

	/**
	 * Computes the next object of the Iterable. Can be implemented to realize
	 * custom behaviour for an iteration process. null is considered as end of data.
	 *
	 * @return the next element of the Iterable.
	 */
	public abstract E computeNext();

}