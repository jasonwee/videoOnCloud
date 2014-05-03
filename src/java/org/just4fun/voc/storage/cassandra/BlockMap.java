package org.just4fun.voc.storage.cassandra;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * The <code>BlockMap</code> keeps track of file blocks by their names. Given
 * that the name is a byte array, we rely on a custom comparator that knows how
 * to compare such names.
 */
public class BlockMap extends TreeMap<byte[], byte[]> {
	private static final long serialVersionUID = 1550200273310875675L;

	/**
	 * Define a block map which is essentially a map of a block name (in the
	 * form of bytes) to the block data (again, in the form of bytes). Given
	 * that byte arrays don't lend themselves to comparison naturally, we pass
	 * it a custom comparator.
	 */
	public BlockMap() {
		super(BYTE_ARRAY_COMPARATOR);
	}

	// A comparator that checks if two byte arrays are the same or not.
	protected static final Comparator<byte[]> BYTE_ARRAY_COMPARATOR = new Comparator<byte[]>() {
		public int compare(byte[] o1, byte[] o2) {
			if (o1 == null || o2 == null) {
				return (o1 != null ? -1 : o2 != null ? 1 : 0);
			}
			if (o1.length != o2.length) {
				return o1.length - o2.length;
			}
			for (int i = 0; i < o1.length; i++) {
				if (o1[i] != o2[i]) {
					return o1[i] - o2[i];
				}
			}
			return 0;
		}
	};

	/**
	 * Put a <key, value> tuple in the block map, where the key is a
	 * {@link java.lang.String}.
	 * 
	 * @param key
	 *            a stringified key
	 * @param value
	 *            a byte array value
	 * @return the previously associated value
	 */
	public byte[] put(String key, byte[] value) {
		return super.put(key.getBytes(), value);
	}

	/**
	 * Put a <key, value> tuple in the block map, where the value is a
	 * {@link java.lang.String}.
	 * 
	 * @param key
	 *            a byte array key
	 * @param value
	 *            a stringified value
	 * @return the previously associated value
	 */
	public byte[] put(byte[] key, String value) {
		return super.put(key, value.getBytes());
	}

	/**
	 * Put a <key, value> tuple in the block map, where both the key and value
	 * are a {@link java.lang.String}.
	 * 
	 * @param key
	 *            a stringified key
	 * @param value
	 *            a stringified value
	 * @return the previously associated value
	 */
	public byte[] put(String key, String value) {
		return super.put(key.getBytes(), value.getBytes());
	}

	/**
	 * Get the value for the given key, which is a {@link java.lang.String}.
	 * 
	 * @param key
	 *            a stringified key
	 * @return the currently associated value
	 */
	public byte[] get(String key) {
		return super.get(key.getBytes());
	}
}
