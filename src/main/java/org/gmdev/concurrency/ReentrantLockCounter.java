package org.gmdev.concurrency;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantLockCounter {
	private final ReentrantLock lock = new ReentrantLock();
	private final ReadWriteLock lockReadWrite = new ReentrantReadWriteLock();

	private int count = 0;

	/**
	 * Thread Safe Increment using ReentrantLock
	 */
    public void increment() {
    	lock.lock();
    	try {
    		count = count + 1;
        } finally {
        	lock.unlock();
        }
    }
    
    /**
     * Thread Safe Increment using ReentrantLock and tryLock() method
     * The tryLock() method tries to acquire the lock without pausing the thread
     * If the thread couldn’t acquire the lock because it was held by some other thread, 
     * then It returns immediately instead of waiting for the lock to be released
     */
    public int incrementAndGet() {
    	// Check if the lock is currently acquired by any thread
        System.out.println("IsLocked : " + lock.isLocked());
        
        // Check if the lock is acquired by the current thread itself.
        System.out.println("IsHeldByCurrentThread : " + lock.isHeldByCurrentThread());

        // Try to acquire the lock
        boolean isAcquired = lock.tryLock();
    	
        System.out.println("Lock Acquired : " + isAcquired + "\n");

        if (isAcquired) {
        	try {
				Thread.sleep(5000);
				count++;
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			} finally {
				lock.unlock();
			}
        }
    	return count;
    }
    
    /**
     * @return int
     */
    public int getCount() {
    	return count;
    }
    
    /** 
     * ReadWriteLock consists of a pair of locks - one for read access and one for write access. 
     * The read lock may be held by multiple threads simultaneously as long as the write lock is not held by any thread.
	 * ReadWriteLock allows for an increased level of concurrency. 
	 * It performs better compared to other locks in applications where there are fewer writes than reads.
     */
    public int incrementAndGetWriteLock() {
    	lockReadWrite.writeLock().lock();
        
    	try {
        	Thread.sleep(100);
            count = count + 1;
            return count;
        } catch (InterruptedException e) {
        	throw new IllegalStateException(e);
        } finally {
        	lockReadWrite.writeLock().unlock();
        }
    }
    
    public int getCountReadLock() {
    	lockReadWrite.readLock().lock();
        
    	try {
            return count;
        } finally {
        	lockReadWrite.readLock().unlock();
        }
    }
	
    
}
