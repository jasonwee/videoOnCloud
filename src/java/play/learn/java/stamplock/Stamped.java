package play.learn.java.stamplock;

import java.util.concurrent.locks.StampedLock;

public class Stamped implements Counter {
	
	private StampedLock rwlock = new StampedLock();
	
	private long counter;
	public long s, t;

	@Override
	public long getCounter() {
		long stamp = rwlock.tryOptimisticRead();
		
		try 
		{
			long result = counter;
			
			if (rwlock.validate(stamp)) {
				return result;				
			}
			
			stamp = rwlock.readLock();
			result = counter;
			rwlock.unlockRead(stamp);
			return counter;
		} finally {}
		
		
	}

	@Override
	public void increment() {
		long stamp = rwlock.writeLock();
		
		try  {
			++counter;
		}
		finally {
			rwlock.unlockWrite(stamp);;
		}
	}

}
