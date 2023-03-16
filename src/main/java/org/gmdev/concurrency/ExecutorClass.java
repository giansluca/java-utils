package org.gmdev.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorClass {
	
	/**
	 * ExecutorService example single Thread
	 */
	public static void executorsExampleSingle() {
		System.out.println("Inside : " + Thread.currentThread().getName());
		System.out.println("Creating Executor Service...");
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		
		System.out.println("Creating a Runnable...");
		Runnable runnable = () -> {
			System.out.println("Inside : " + Thread.currentThread().getName());
        };
		
        System.out.println("Submit the task specified by the runnable to the executor service.");
        executorService.execute(runnable);
        // The program never exits, because, the executor service keeps listening for new tasks until we shut it down explicitly.
        System.out.println("Shutting down the executor");
        
        executorService.shutdown();
	}
	
	/**
	 * ExecutorService example multiple Threads
	 */
	public static void executorsExampleMulti() {
		System.out.println("Inside : " + Thread.currentThread().getName());
        System.out.println("Creating Executor Service with a thread pool of Size 2");
        ExecutorService executorService = Executors.newFixedThreadPool(2);
		
		Runnable task_1 = () -> {
			System.out.println("Executing Task1 inside : " + Thread.currentThread().getName());
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException ex) {
				throw new IllegalStateException(ex);
			}
		};
		
		Runnable task_2 = () -> {
			System.out.println("Executing Task2 inside : " + Thread.currentThread().getName());
			try {
				TimeUnit.SECONDS.sleep(4);
			} catch (InterruptedException ex) {
				throw new IllegalStateException(ex);
			}
        };
        
        Runnable task_3 = () -> {
        	System.out.println("Executing Task3 inside : " + Thread.currentThread().getName());
        	try {
        		TimeUnit.SECONDS.sleep(3);
        	} catch (InterruptedException ex) {
        		throw new IllegalStateException(ex);
        	}
        };
        
        System.out.println("Submitting the tasks for execution...");
        executorService.execute(task_1);
        executorService.execute(task_2);
        executorService.execute(task_3);
        
        executorService.shutdown();
	}
	
	/**
	 * ScheduledExecutorService delay example
	 * Executes the task after 5 seconds from the time of submission
	 */
	public static void scheduledExecutorsExample() {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		
		Runnable task = () -> {
			System.out.println("Executing Task At " + System.nanoTime());
			
			try {
				TimeUnit.SECONDS.sleep(3);
				System.out.println("Done!");
        	} catch (InterruptedException ex) {
        		throw new IllegalStateException(ex);
        	}
		};
		
		System.out.println("Submitting task at " + System.nanoTime() + " to be executed after 5 seconds.");
		
		scheduledExecutorService.schedule(task, 5, TimeUnit.SECONDS);
		
		scheduledExecutorService.shutdown();
	}
	
	/**
	 * ScheduledExecutorService periodic example
	 * Executes the task every 2 seconds and after 0 second from the submission
	 * The task will only terminate if you either shut down the executor or kill the program
	 */
	public static void ScheduledExecutorsPeriodicExample() {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		Runnable task = () -> {
			System.out.println("Executing Task At " + System.nanoTime());
		};
		
		System.out.println("scheduling task to be executed every 2 seconds with an initial delay of 0 seconds");
		
        scheduledExecutorService.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
	}
	
	
}
