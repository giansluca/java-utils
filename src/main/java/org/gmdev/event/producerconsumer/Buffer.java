package org.gmdev.event.producerconsumer;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
	
    private final Queue<Integer> list;
    private final int size;
    
    public Buffer(int size) {
        this.list = new LinkedList<>();
        this.size = size;
    }
    
    public synchronized void add(int value) throws InterruptedException {
    	while (list.size() >= size) {
    		// wait from Consumer Thread
    		wait();
    	}
    	
    	// adding a new element to the buffer
    	list.add(value);
    	
    	// notify the consumer
    	notifyAll();
    }
    
    public synchronized int poll() throws InterruptedException {
    	while (list.size() == 0) {
    		// wait notification from Producer Thread
    		wait();
    	}
    	
    	// getting an element from the buffer
    	int value = list.poll();
    	
    	// notify the producer
    	notifyAll();
    	
    	return value;
    }
    
    public synchronized int getCurrentBufferSize() {
    	return this.list.size();
    }
    

}
