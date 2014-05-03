package org.just4fun.voc.storage.cassandra;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * The <code>FileDescriptor</code> captures the meta-data of the file, a la Unix
 * inodes (index nodes). In addition to the usual tidbits such as name, length
 * and timestamps, it carries a flag (@see {@link #deleted}) that indicates
 * whether the file has been deleted or not.
 * 
 * <p>
 * The data in the file is indexed through a ordered list of {@link FileBlock}
 * s, where each block denotes a contiguous portion of the file data. By walking
 * through the blocks sequentially in the given order, one can retrieve the
 * entire contents of the file. A psuedo-random access of the file can be
 * effected by loading into memory the entire block to which the (random) file
 * pointer maps, and then positioning the file pointer within the in-memory
 * block.
 * </p>
 */
public class FileDescriptor {

	// The name of the file.
	private String name;

	// The length of the file.
	private long length;

	// A flag indicating whether the file has been deleted.
	private boolean deleted;

	// The timestamp at which the file was last modified.
	private long lastModified;

	// The timestamp at which the file was last accessed.
	private long lastAccessed;

	// The maximum size of the block that may be stored in a column.
	private long blockSize;

	// The ordered list of blocks in this file.
	private LinkedList<FileBlock> blocks;

	// The number to use for the next block that will be allocated. If it is
	// uninitialized (i.e., -1), then it forces the descriptor to reset it
	// to the highest number of any block in {@link #blocks}.
	private int nextBlockNumber = -1;

	/**
	 * Construct a file descriptor for the given file name and block size.
	 * 
	 * @param fileName
	 *            the name of the file
	 * @param blockSize
	 *            the size of the block
	 */
	public FileDescriptor(String fileName, long blockSize) {
		setName(fileName);
		setLength(0);
		Date now = new Date();
		setLastAccessed(now.getTime());
		setLastModified(now.getTime());
		setBlockSize(blockSize);
		setBlocks(new LinkedList<FileBlock>());
	}

	/**
	 * @return the name of the file
	 */
	public String getName() {
		return name;
	}

	/**
	 * Rename the file.
	 * 
	 * @param name
	 *            the name of the file
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the current length of the file
	 */
	public long getLength() {
		return length;
	}

	/**
	 * Resize the file.
	 * 
	 * @param length
	 *            the new length of the file
	 */
	public void setLength(long length) {
		this.length = length;
	}

	/**
	 * @return true if the file has been deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * Mark the file as deleted (or undeleted) based on whether the given flag
	 * is true (or not).
	 * 
	 * @param deleted
	 *            should the file be marked as deleted?
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the timestamp at which the file was last modified
	 */
	public long getLastModified() {
		return lastModified;
	}

	/**
	 * Set the timestamp at which the file was last modified.
	 * 
	 * @param lastModified
	 *            the last modified timestamp
	 */
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * @return the timestamp at which the file was last accessed
	 */
	public long getLastAccessed() {
		return lastAccessed;
	}

	/**
	 * Set the timestamp at which the file was last accessed.
	 * 
	 * @param lastModified
	 *            the last accessed timestamp
	 */
	public void setLastAccessed(long lastAccessed) {
		this.lastAccessed = lastAccessed;
	}

	/**
	 * @return the maximum size of the block that may be stored in a column
	 */
	public long getBlockSize() {
		return blockSize;
	}

	/**
	 * Set the maximum size of the block that may be stored in a column.
	 * 
	 * @param blockSize
	 *            the block size
	 */
	public void setBlockSize(long blockSize) {
		this.blockSize = blockSize;
	}

	/**
	 * @return the ordered list of file blocks
	 */
	public List<FileBlock> getBlocks() {
		return blocks;
	}

	/**
	 * Set the ordered list of file blocks.
	 * 
	 * @param blocks
	 *            the ordered list of file blocks
	 */
	public void setBlocks(List<FileBlock> blocks) {
		if (LinkedList.class.isAssignableFrom(blocks.getClass())) {
			this.blocks = (LinkedList<FileBlock>) blocks;
		} else {
			this.blocks = new LinkedList<FileBlock>(blocks);
		}
	}

	/**
	 * @return the first block in the file
	 */
	public FileBlock getFirstBlock() {
		if (blocks.isEmpty()) {
			blocks.add(createBlock());
		}
		return blocks.getFirst();
	}

	/**
	 * @return the last block in the file
	 */
	public FileBlock getLastBlock() {
		if (blocks.isEmpty()) {
			blocks.add(createBlock());
		}
		return blocks.getLast();
	}

	/**
	 * @return true if the given block is the first one in the file
	 */
	public boolean isFirstBlock(FileBlock nextBlock) {
		return getFirstBlock().equals(nextBlock);
	}

	/**
	 * @return true if the given block is the last one in the file
	 */
	public boolean isLastBlock(FileBlock nextBlock) {
		return getLastBlock().equals(nextBlock);
	}

	/**
	 * Return the block that logically follows the given block.
	 * 
	 * @param block
	 *            an existing file block
	 * @return the block that logically follows the given block
	 */
	public FileBlock getNextBlock(FileBlock block) {
		int blockIndex = blocks.indexOf(block);
		return (blockIndex != -1 && blockIndex < (blocks.size() - 1)) ? blocks
				.get(blockIndex + 1) : null;
	}

	public FileBlock getPreviousBlock(FileBlock block) {
		int blockIndex = blocks.indexOf(block);

		if (blockIndex != -1 && blockIndex < (blocks.size() - 1)) {
			// found and return currentblock + 1
			return blocks.get(blockIndex - 1);
		} else {
			// not found, return null
			return null;
		}
	}

	/**
	 * Add the given block as the last block in the file.
	 * 
	 * @param newBlock
	 *            the block to be appended to the file
	 */
	public void addLastBlock(FileBlock newBlock) {
		blocks.addLast(newBlock);
	}

	/**
	 * Add the given block as the first block in the file.
	 * 
	 * @param newBlock
	 *            the block to be prepended to the file
	 */
	public void addFirstBlock(FileBlock newBlock) {
		blocks.addFirst(newBlock);
	}

	/**
	 * Insert a new block either after or before an existing block, based on
	 * whether or not the insertAfter flag is true.
	 * 
	 * @param existingBlock
	 *            an existing file block
	 * @param newBlock
	 *            a new file block
	 * @param insertAfter
	 *            whether or not to insert new block after the existing block
	 */
	public void insertBlock(FileBlock existingBlock, FileBlock newBlock,
			boolean insertAfter) {
		int existingIndex = blocks.indexOf(existingBlock);
		if (existingIndex == -1) {
			blocks.add(newBlock);
		} else {
			if (insertAfter) {
				blocks.add(existingIndex + 1, newBlock);
			} else {
				blocks.add(existingIndex, newBlock);
			}
		}
	}

	/**
	 * Replace an existing block with a new block.
	 * 
	 * @param existingBlock
	 *            an existing file block
	 * @param newBlock
	 *            a new file block
	 */
	public void replaceBlock(FileBlock existingBlock, FileBlock newBlock) {
		int existingIndex = blocks.indexOf(existingBlock);
		if (existingIndex != -1) {
			blocks.remove(existingIndex);
			blocks.add(existingIndex, newBlock);
		}
	}

	/**
	 * Remove an existing block.
	 * 
	 * @param existingBlock
	 *            an existing block
	 * @return the index of the block just removed
	 */
	public int removeBlock(FileBlock existingBlock) {
		int existingIndex = blocks.indexOf(existingBlock);
		if (existingIndex != -1) {
			blocks.remove(existingBlock);
		}
		return existingIndex;
	}

	/**
	 * Create a file block with no data in it. The block number assigned to new
	 * blocks is set to auto-increment.
	 * 
	 * @return the newly created file block
	 */
	public FileBlock createBlock() {
		FileBlock newBlock = new FileBlock();
		newBlock.setBlockName(getNextBlockNumber());
		newBlock.setBlockSize(getBlockSize());
		newBlock.setDataLength(0);
		return newBlock;
	}

	/**
	 * Return the block number to use for the next block that is allocated. This
	 * number starts from 0 and will auto-increment with each block allocation.
	 * 
	 * @return the next block number to use
	 */
	public int getNextBlockNumber() {
		if (nextBlockNumber == -1) {
			for (FileBlock fileBlock : blocks) {
				nextBlockNumber = Math.max(nextBlockNumber,
						fileBlock.getBlockNumber());
			}
		}
		return ++nextBlockNumber;
	}

}
