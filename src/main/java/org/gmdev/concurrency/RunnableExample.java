package org.gmdev.concurrency;

public class RunnableExample implements Runnable {

	@Override
	public void run() {
		System.out.println("Inside : " + Thread.currentThread().getName());
	}
	
	
}
