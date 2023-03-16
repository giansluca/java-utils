package org.gmdev.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
	
	private AtomicInteger count = new AtomicInteger(0);
	
	/**
	 * The AtomicInteger.incrementAndGet() method is atomic, so you can safely call it from several threads simultaneously
	 * and be sure that the access to the count variable will be synchronized.
	 */
    public int incrementAndGet() {
        return count.incrementAndGet();
    }
    
    public int getCount() {
        return count.get();
    }
    
}
