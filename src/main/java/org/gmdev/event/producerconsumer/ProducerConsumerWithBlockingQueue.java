package org.gmdev.event.producerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class ProducerConsumerWithBlockingQueue {

	public void startNormalThread() {
		BlockingQueue<Integer> blockingQueue = new LinkedBlockingDeque<>(5);
        
		Thread producerThread = new Thread(() -> {
        	try {
        		int value = 0;
        		while (true) {
        			blockingQueue.put(value);
        			System.out.println("Produced " + value);
        			
        			value++;
        			Thread.sleep(1000);
        		}
        	} catch (InterruptedException e) {
        		e.printStackTrace();
        	}
        });
		
		Thread consumerThread = new Thread(() -> {
			try {
				while (true) {
					int value = blockingQueue.take();
					System.out.println("Consume " + value);
					
					Thread.sleep(5000);
				}
			} catch (InterruptedException e) {
            	e.printStackTrace();
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
	
	public void startServiceExecutor() {
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingDeque<>(5);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        Runnable producerTask = () -> {
        	try {
        		int value = 0;
        		while (true) {
        			blockingQueue.put(value);
        			System.out.println("Produced " + value);
        			
        			value++;
        			Thread.sleep(1000);
        		}
        	} catch (InterruptedException e) {
        		e.printStackTrace();
        	}
        };
        
        Runnable consumerTask = () -> {
        	try {
				while (true) {
					int value = blockingQueue.take();
					System.out.println("Consume " + value);
					
					Thread.sleep(5000);
				}
			} catch (InterruptedException e) {
            	e.printStackTrace();
            }
        };
        
        executor.execute(producerTask);
        executor.execute(consumerTask);
        executor.shutdown();
	}
	
	
}
