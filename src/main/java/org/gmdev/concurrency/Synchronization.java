package org.gmdev.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Synchronization {
	
	private static boolean sayHello = false;
	private volatile static boolean sayHelloVolatile = false;
	
	/**
	 * Thread Interference Errors (Race Conditions)
	 */
	public static void raceConditionExample() throws InterruptedException {
		Counter counter = new Counter();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
   
        for (int i = 0; i < 1000; i++) {
        	executorService.submit(() -> {
        		// Race Conditions
        		counter.increment();
        	});
        }
        
        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);
        System.out.println("Final count is : " + counter.getCount());
	}
	
	/**
	 * The synchronized keyword makes sure that only one thread can enter the increment() method at one time.
	 */
	public static void synchronizedMethodExample() throws InterruptedException {
		Counter counter = new Counter();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
   
        for (int i = 0; i < 1000; i++) {
        	executorService.submit(() -> {
        		// Calling synchronized method
        		counter.incrementSync();
        	});
        }
        
        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);
        System.out.println("Final count is : " + counter.getCount());
	}
	
	/**
	 * Memory Consistency Errors 
	 */
	public static void memoryConsistencyErrorExample() throws InterruptedException {
		Thread thread = new Thread(() -> {
			while (!sayHello) {
			}
			System.out.println("Hello World!");
			while (sayHello) {
			}
			System.out.println("Good Bye!");
		});
		
		thread.start();

        Thread.sleep(1000);
        System.out.println("Say Hello..");
        sayHello = true;

        Thread.sleep(1000);
        System.out.println("Say Bye..");
        sayHello = false;
	}
	
	/**
	 * Volatile keyword is used to avoid memory consistency errors in multithread programs. 
	 * It tells the compiler to avoid doing any optimizations to the variable.
	 * The variable’s value will always be read from the main memory instead of temporary registers
	 */
	public static void volatileKeywordExample() throws InterruptedException {
		Thread thread = new Thread(() -> {
			while (!sayHelloVolatile) {
			}
			
			System.out.println("Hello World!");
			
			while (sayHelloVolatile) {
			}
			
			System.out.println("Good Bye!");
		});
		
		thread.start();

        Thread.sleep(1000);
        System.out.println("Say Hello..");
        sayHelloVolatile = true;

        Thread.sleep(1000);
        System.out.println("Say Bye..");
        sayHelloVolatile = false;
	}
	
	/**
	 * ReentrantLock is a mutually exclusive lock with the same behavior as the intrinsic/implicit 
	 * lock accessed via the synchronized keyword. 
	 */
	public static void reentrantLocExample() throws InterruptedException {
		ReentrantLockCounter lockCounter = new ReentrantLockCounter();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
   
        for (int i = 0; i < 1000; i++) {
        	executorService.submit(() -> {
        		lockCounter.increment();
        	});
        }
        
        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);
        System.out.println("Final count is : " + lockCounter.getCount());	
	}
	
	/**
	 * The ReentrantLock also provides various methods for more fine-grained control  
	 */
	public static void reentrantLockMethodsCounter() throws InterruptedException {
		ReentrantLockCounter lockMethodsCounter = new ReentrantLockCounter();
		ExecutorService executorService = Executors.newFixedThreadPool(2);
   
		executorService.submit(() ->   {
			System.out.println("IncrementCount (First Thread) : " +lockMethodsCounter.incrementAndGet() + "\n");
		});
		
		// using a Thread.sleep() here greater than the Thread.sleep() in lock method i can get both the treads acquiring the lock
		// Thread.sleep(8000);
		
		executorService.submit(() ->   {
			System.out.println("IncrementCount (Second Thread) : " +lockMethodsCounter.incrementAndGet() + "\n");
		});
		
		executorService.shutdown();
	}
	
	/**
	 * Using ReadWriteLock for read access and write access.
	 */
	public static void reentrantReadWriteLock() throws InterruptedException {
		ReentrantLockCounter lockCounter = new ReentrantLockCounter();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		
        for (int i = 0; i < 10; i++) {
        	executorService.submit(() -> {
        		int counter = lockCounter.incrementAndGetWriteLock();
        		System.out.println("Writing operation - counter is : " + counter);
        	});
        	
        	Thread.sleep(1000);
        	
        	executorService.submit(() -> {
        		int counter = lockCounter.getCount();
        		System.out.println("Reading operation - counter is : " + counter);
        	});
        }
        
        executorService.shutdown();
	}
	
	/**
	 * AtomicInteger class to make sure that the increment to the count variable happens atomically.
	 * You should use these Atomic classes instead of synchronized keyword and locks whenever possible 
	 * because they are faster, easier to use, readable and scalable. 
	 */
	public static void atomicIntegerExample() throws InterruptedException {
		AtomicCounter atomicCounter = new AtomicCounter();
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		
		for (int i = 0; i < 1000; i++) {
			executorService.submit(() -> { 
				atomicCounter.incrementAndGet();
			});
		}
		
		executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);
        System.out.println("Final Count is : " + atomicCounter.getCount());
	}
	
	
}
