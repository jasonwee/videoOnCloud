package play.learn.java.design.read_writer_lock;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class ReaderWriterLock implements ReadWriteLock {

	private Object readerMutex = new Object();

	private int currentReaderCount;

	/**
	 * Global mutex is used to indicate that whether reader or writer gets the lock
	 * in the moment.
	 * <p>
	 * 1. When it contains the reference of {@link #readerLock}, it means that the
	 * lock is acquired by the reader, another reader can also do the read operation
	 * concurrently. <br>
	 * 2. When it contains the reference of reference of {@link #writerLock}, it
	 * means that the lock is acquired by the writer exclusively, no more reader or
	 * writer can get the lock.
	 * <p>
	 * This is the most important field in this class to control the access for
	 * reader/writer.
	 */
	private Set<Object> globalMutex = new HashSet<>();

	private ReadLock readerLock = new ReadLock();
	private WriteLock writerLock = new WriteLock();

	@Override
	public Lock readLock() {
		return readerLock;
	}

	@Override
	public Lock writeLock() {
		return writerLock;
	}

	/**
	 * return true when globalMutex hold the reference of writerLock
	 */
	private boolean doesWriterOwnThisLock() {
		return globalMutex.contains(writerLock);
	}

	/**
	 * Nobody get the lock when globalMutex contains nothing
	 * 
	 */
	private boolean isLockFree() {
		return globalMutex.isEmpty();
	}

	/**
	 * Reader Lock, can be access for more than one reader concurrently if no writer
	 * get the lock
	 */
	private class ReadLock implements Lock {

		@Override
		public void lock() {
			synchronized (readerMutex) {
				currentReaderCount++;
				if (currentReaderCount == 1) {
					acquireForReaders();
				}
			}
		}

		/**
		 * Acquire the globalMutex lock on behalf of current and future concurrent
		 * readers. Make sure no writers currently owns the lock.
		 */
		private void acquireForReaders() {
			// Try to get the globalMutex lock for the first reader
			synchronized (globalMutex) {
				// If the no one get the lock or the lock is locked by reader, just set the
				// reference
				// to the globalMutex to indicate that the lock is locked by Reader.
				while (doesWriterOwnThisLock()) {
					try {
						globalMutex.wait();
					} catch (InterruptedException e) {
						System.out.println("InterruptedException while waiting for globalMutex in acquireForReaders" + e);
						Thread.currentThread().interrupt();
					}
				}
				globalMutex.add(this);
			}
		}

		@Override
		public void unlock() {

			synchronized (readerMutex) {
				currentReaderCount--;
				// Release the lock only when it is the last reader, it is ensure that the lock
				// is released
				// when all reader is completely.
				if (currentReaderCount == 0) {
					synchronized (globalMutex) {
						// Notify the waiter, mostly the writer
						globalMutex.remove(this);
						globalMutex.notifyAll();
					}
				}
			}

		}

		@Override
		public void lockInterruptibly() throws InterruptedException {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean tryLock() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
			throw new UnsupportedOperationException();
		}

		@Override
		public Condition newCondition() {
			throw new UnsupportedOperationException();
		}

	}

	/**
	 * Writer Lock, can only be accessed by one writer concurrently
	 */
	private class WriteLock implements Lock {

		@Override
		public void lock() {

			synchronized (globalMutex) {

				// Wait until the lock is free.
				while (!isLockFree()) {
					try {
						globalMutex.wait();
					} catch (InterruptedException e) {
						System.out.println("InterruptedException while waiting for globalMutex to begin writing" + e);
						Thread.currentThread().interrupt();
					}
				}
				// When the lock is free, acquire it by placing an entry in globalMutex
				globalMutex.add(this);
			}
		}

		@Override
		public void unlock() {

			synchronized (globalMutex) {
				globalMutex.remove(this);
				// Notify the waiter, other writer or reader
				globalMutex.notifyAll();
			}
		}

		@Override
		public void lockInterruptibly() throws InterruptedException {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean tryLock() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
			throw new UnsupportedOperationException();
		}

		@Override
		public Condition newCondition() {
			throw new UnsupportedOperationException();
		}
	}

}
