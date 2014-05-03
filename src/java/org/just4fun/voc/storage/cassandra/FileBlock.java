package org.just4fun.voc.storage.cassandra;

/**
 * A <code>FileBlock</code> denotes a partition of the file whose size is capped
 * by a certain limit (@see {@link #blockSize}). In particular, it refers to a
 * contiguous sequence of bytes, which is stored as the value of a column in the
 * column family that represents this directory.
 * 
 * <p>
 * Ideally, each block in the file (except for maybe the last one) should
 * contain as many bytes as {@link #blockSize}. However, at times, blocks tend
 * to become fragmented in the sense that the {@link blockSize} bytes that was
 * supposed to go in one (ideal) block gets spread across multiple blocks, each
 * containing a portion of the ideal block. This may happen when writes occur
 * after the file pointer is randomly moved across the file. While there are
 * ways to avoid or at least mitigate the fragmentation issue, we currently deal
 * with it as a part of life. In the event a block represents a fragment, it is
 * important to know the offset of the fragment relative to the block where the
 * (fragmented) block begins. This information is captured in
 * {@link FileBlock#dataOffset}.
 * </p>
 * 
 * <p>
 * There is some file block information that is not actually saved in cassandra,
 * but rather calculated on the fly. For example, the
 * {@link FileBlock#blockOffset} is used to keep track of the offset of the
 * block relative to the file in. Similarly, the {@link FileBlock#dataPosition}
 * notes where the file pointer is positioned relative to the block. Both of
 * these fields are calculated after the file descriptor is loaded from
 * Cassandra.
 * </p>
 */
public class FileBlock implements Cloneable {
	// The name of the file block.
	private String blockName;

	// The number of the file block.
	private int blockNumber;

	// The maximum size of the file block.
	private long blockSize;

	// The offset of the first byte in this block relative to the file.
	private long blockOffset;

	// The offset within the file block range where the first data byte appears.
	private long dataOffset;

	// The length of the data starting from {@link #dataOffset}
	private int dataLength;

	// The position of the file pointer relative to this block, assuming that
	// pointer is currently inside this block to begin with.
	private int dataPosition;

	protected static final String BLOCK_COLUMN_NAME_PREFIX = "BLOCK-";

	/**
	 * Construct an empty file block.
	 */
	public FileBlock() {

	}

	/**
	 * @return the name of the file block
	 */
	public String getBlockName() {
		return blockName;
	}

	/**
	 * Set the name of the file block.
	 * 
	 * @param blockName
	 *            the name of the file block
	 */
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	/**
	 * Set the name of the file block based on the given block number.
	 * 
	 * @param blockNumber
	 *            the block number
	 */
	public void setBlockName(int blockNumber) {
		this.blockNumber = blockNumber;
		this.blockName = createBlockName(blockNumber);
	}

	/**
	 * @return the number of this file block
	 */
	public int getBlockNumber() {
		return blockNumber;
	}

	/**
	 * Set the number of this file block.
	 * 
	 * @param blockNumber
	 *            the number for this file block
	 */
	public void setBlockNumber(int blockNumber) {
		this.blockNumber = blockNumber;
	}

	/**
	 * @return the maximum size of this file block
	 */
	public long getBlockSize() {
		return blockSize;
	}

	/**
	 * Set the maximum size of this file block.
	 * 
	 * @param blockSize
	 *            the maximum block size
	 */
	public void setBlockSize(long blockSize) {
		this.blockSize = blockSize;
	}

	/**
	 * @return the offset of the first byte in this block relative to the file
	 */
	public long getBlockOffset() {
		return blockOffset;
	}

	/**
	 * Set the offset of the first byte in this block relative to the file.
	 * 
	 * @param blockOffset
	 *            the new offset for this file block
	 */
	public void setBlockOffset(long blockOffset) {
		this.blockOffset = blockOffset;
	}

	/**
	 * @return the offset within the file block range where the first data byte
	 *         appears
	 */
	public long getDataOffset() {
		return dataOffset;
	}

	/**
	 * Set the offset within the file block range where the first data byte
	 * appears.
	 * 
	 * @param dataOffset
	 *            the data offset
	 */
	public void setDataOffset(long dataOffset) {
		this.dataOffset = dataOffset;
	}

	/**
	 * @return the length of the data starting from {@link #dataOffset}
	 */
	public int getDataLength() {
		return dataLength;
	}

	/**
	 * Set the length of the data starting from {@link #dataOffset}.
	 * 
	 * @param dataLength
	 *            the new data length
	 */
	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	/**
	 * Calculate the offset of the last byte of data in this file block relative
	 * to the block.
	 * 
	 * @return the last byte's offset relative to the block
	 */
	public long getLastDataOffset() {
		return getDataOffset() + getDataLength();
	}

	/**
	 * @return the position of the file pointer relative to this block, assuming
	 *         that pointer is currently inside this block to begin with
	 */
	public int getDataPosition() {
		return dataPosition;
	}

	/**
	 * Set the position of the file pointer relative to this block, assuming
	 * that pointer is currently inside this block to begin with
	 * 
	 * @param dataPosition
	 *            the new data position
	 */
	public void setDataPosition(int dataPosition) {
		this.dataPosition = dataPosition;
	}

	public long getPositionOffset() {
		return getDataOffset() + getDataPosition();
	}

	/**
	 * Create a readable name of the block, derived from it's block number.
	 * 
	 * @param blockNumber
	 *            the number of the block to use in the name
	 * @return
	 */
	private static String createBlockName(int blockNumber) {
		return BLOCK_COLUMN_NAME_PREFIX + blockNumber;
	}

	/**
	 * @return a shallow clone of this file block
	 */
	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return "blockName " + blockName + " blockNumber " + blockNumber
				+ " blockSize " + blockSize + " blockOffset " + blockOffset
				+ " dataOffset " + dataOffset + " dataLength " + dataLength
				+ " dataPosition " + dataPosition;
	}
}
