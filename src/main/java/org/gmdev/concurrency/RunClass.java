package org.gmdev.concurrency;

public class RunClass {
	
	/**
	 * Thread using extending Thread class and overriding the run() method 
	 * This way is limited, better use Implementation of Runnable interface
	 */
	public static void threadExample() {
		System.out.println("Inside : " + Thread.currentThread().getName());
		
		System.out.println("Creating Thread...");
		Thread thread = new ThreadExample("My Thread");
		
		System.out.println("Starting Thread...");
        thread.start();
	}
	
	/**
	 * Thread implementing Runnable interface and implementing the run() methods
	 * Better way than Extending the Thread class
	 */
	public static void runnableExample() {
		System.out.println("Inside : " + Thread.currentThread().getName());
        System.out.println("Creating Runnable...");
        Runnable runnable = new RunnableExample();
        
        System.out.println("Creating Thread...");
        Thread thread = new Thread(runnable, "My Thread");
        
        System.out.println("Starting Thread...");
        thread.start();
	}
	
	/**
	 * Thread using Runnable and Anonymous class
	 */
	public static void runnableExampleAnonymous() {
		System.out.println("Inside : " + Thread.currentThread().getName());
        System.out.println("Creating Runnable...");
        
        Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("Inside : " + Thread.currentThread().getName());
				
			}
		};
		
		System.out.println("Creating Thread...");
        Thread thread = new Thread(runnable, "My Thread");
        
        System.out.println("Starting Thread...");
        thread.start();
	}
	
	/**
	 * Thread using Runnable Anonymous class and start in a compact syntax 
	 */
	public static void runnableExampleAnonymousCompact() {
		System.out.println("Inside : " + Thread.currentThread().getName());
        System.out.println("Creating Thread, Runnable, and Start it ...");
		
        new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Inside : " + Thread.currentThread().getName());
			}
		}, "My Thread").start();
	}
	
	/**
	 * Thread using Runnable and Java 8 Lambda expression
	 */
	public static void RunnableExampleLambdaExpression() {
		System.out.println("Inside : " + Thread.currentThread().getName());
        System.out.println("Creating Runnable...");
        
        Runnable runnable = () -> {
            System.out.println("Inside : " + Thread.currentThread().getName());
        };
        
        System.out.println("Creating Thread...");
        Thread thread = new Thread(runnable, "Thread");
        
        System.out.println("Starting Thread...");
        thread.start();
	}
	
	/**
	 * Pausing execution of a Thread using sleep().
	 * It pause the execution of the currently executing thread for the specified number of milliseconds.
	 */
	public static void threadSleepExample() {
		System.out.println("Inside : " + Thread.currentThread().getName());
		
        String[] messages = {"If I can stop one heart from breaking,",
                "I shall not live in vain.",
                "If I can ease one life the aching,",
                "Or cool one pain,",
                "Or help one fainting robin",
                "Unto his nest again,",
                "I shall not live in vain"};
        
        Runnable runnable = () -> {
        	System.out.println("Inside : " + Thread.currentThread().getName());
        	for (String message : messages) {
        		System.out.println(message);
        		try {
        			Thread.sleep(2000);
        		} catch (InterruptedException e) {
        			throw new IllegalStateException(e);
				}
        	}
        };
        
        Thread thread = new Thread(runnable, "My Thread");
        thread.start();
	}
	
	/**
	 * Waiting for completion of another thread using join()
	 */
	public static void threadJoinExample() {
		// Create Thread 1
        Thread thread_1 = new Thread(() -> {
        	System.out.println("Entered Thread 1");
            
        	try {
            	Thread.sleep(4000);
            } catch (InterruptedException e) {
            	throw new IllegalStateException(e);
            }
           
            System.out.println("Exiting Thread 1");
        });

        // Create Thread 2
        Thread thread_2 = new Thread(() -> {
        	System.out.println("Entered Thread 2");
            
        	try {
            	Thread.sleep(4000);
            } catch (InterruptedException e) {
            	throw new IllegalStateException(e);
            }
            
        	System.out.println("Exiting Thread 2");
        });

        System.out.println("Starting Thread 1");
        thread_1.start();
		
        System.out.println("Waiting for Thread 1 to complete");
        
        try {
        	// wait the end of thread 1
        	thread_1.join();
        } catch (InterruptedException e) {
        	throw new IllegalStateException(e);
        }

        System.out.println("Waited enough! Starting Thread 2 now");
        thread_2.start();
	}
	
	
}
