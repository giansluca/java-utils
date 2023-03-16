package org.gmdev.concurrency;

public class ThreadExample extends Thread {
	
	public ThreadExample(String name) { 
        // call to constructor of the Thread class. 
        super(name); 
    } 
	
	public ThreadExample() {}

	@Override
	public void run() {
        System.out.println("Inside : " + Thread.currentThread().getName());
    }

	
}
