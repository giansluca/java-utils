package org.gmdev.event.producerconsumer;

public class ClassicProducerConsumer {
	
	public void start() {
		Buffer buffer = new Buffer(5);
		
		// Producer
        Thread producerThread = new Thread(new Runnable() {
        	@Override
            public void run() {
        		int value = 0;
        		while (true) {
        			try {
        				buffer.add(value);
        				System.out.println("Produced " + value);
						
        				value++;
	                	Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
        		}		
            }
        });
		
        // Consumer
		Thread consumerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						int value = buffer.poll();
						System.out.println("Consume " + value);
						
						Thread.sleep(6000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
        producerThread.start();
        consumerThread.start();
        
        try {
			producerThread.join();
			consumerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
	}

}
