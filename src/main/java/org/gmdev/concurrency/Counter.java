package org.gmdev.concurrency;

public class Counter {
	
	int count = 0;
	
	/**
	 * increment count by 1
	 */
    public void increment() {
    	count = count + 1;
    }
    
    /**
     * Synchronized Method increment count by 1
     */
    public synchronized void incrementSync() {
        count = count + 1;
    }
    
    public int getCount() {
    	return count;
    }
    
    
}
