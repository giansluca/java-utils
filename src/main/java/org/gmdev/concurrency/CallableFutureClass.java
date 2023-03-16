package org.gmdev.concurrency;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CallableFutureClass {
	
	/**
	 * Simple Future Callable implementations
	 */
	public static void futureAndCallableExample() throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
        
		Callable<String> callable = () -> {
			// Perform some computation
        	System.out.println("Entered Callable");
        	Thread.sleep(2000);
        	return "Hello from Callable";
        };
		
        System.out.println("Submitting Callable");
        Future<String> future = executorService.submit(callable);
		
        // This line executes immediately
        System.out.println("Do something else while callable is getting executed");

        System.out.println("Retrieve the result of the future");
        // Future.get() blocks until the result is available
        String result = future.get();
        System.out.println(result);
        executorService.shutdown();
	}
	
	/**
	 * Future Callable implementations with IsDone() example, obtaining the Future using Lambda Expression
	 */
	public static void futureIsDoneExample()throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		
		// Using ExecutorService with Lambda Expression
        Future<String> future = executorService.submit(() -> {
        	Thread.sleep(2000);
        	return "Hello from Callable";
        });
        
        while (!future.isDone()) {
        	System.out.println("Task is still not done...");
        	Thread.sleep(200);
        }
        
        System.out.println("Task completed! Retrieving the result");
        String result = future.get();
        System.out.println(result);
        executorService.shutdown();
	}
	
	/**
	 * Future Callable implementation, cancel a future using Future.cancel() method
	 */
	public static void futureCancelExample() throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		long startTime = System.nanoTime();
        
		Future<String> future = executorService.submit(() -> {
        	Thread.sleep(4000);
        	return "Hello from Callable";
        });
        
        while (!future.isDone()) {
        	System.out.println("Task is still not done...");
        	Thread.sleep(200);
        	double elapsedTimeInSec = (System.nanoTime() - startTime) / 1000000000.0;
        	
        	// Checking an event to cancel the Task
        	if (elapsedTimeInSec > 10) {
        		System.out.println("Cancelling the Task");
        		future.cancel(true);
        	}
        }
        
        // I need to check if the Task was cancelled or i will get a CancellationException calling future.get() on a cancelled Future
        if (!future.isCancelled()) {
        	System.out.println("Task completed! Retrieving the result");
        	// I can add a Timeout for the get() to avoid forever blocking issues
        	// In this example the timeout of 2 seconds is never triggered (the task is always done when i call the Future.get()), 
        	// To have the Timeout exception to happens i have to comment the while block
        	String result = null;
        	
        	try {
        		result = future.get(2, TimeUnit.SECONDS);
        	} catch (TimeoutException e) {
        		// the Executor service keeps running, in this case i want to shut it down if the timeout happens
        		executorService.shutdown();
        		e.printStackTrace();
			}
        	
			System.out.println(result);
        } else {
        	System.out.println("Task was cancelled");
        }
        executorService.shutdown();
	}
	
	/**
	 * Future Callable implementation, submit multiple tasks and wait for all of them to complete
	 */
	public static void invokeAllExample() throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(5);

        Callable<String> task1 = () -> {
        	Thread.sleep(2000);
        	return "Result of Task1";
        };
		
        Callable<String> task2 = () -> {
        	Thread.sleep(1000);
        	return "Result of Task2";
        };
        
        Callable<String> task3 = () -> {
        	Thread.sleep(5000);
        	return "Result of Task3";
        };
		
        List<Callable<String>> taskList = Arrays.asList(task1, task2, task3);
        
        // submitting all the tasks as a List of Callable and get a List of Future
        System.out.println("Submitting all the tasks for execution...");
        List<Future<String>> futures = executorService.invokeAll(taskList);
		
        for (Future<String> future : futures) {
        	// The result is printed only after all the futures are complete. (i.e. after 5 seconds)
        	System.out.println(future.get());
        }
        executorService.shutdown();
	}
	
	/**
	 * Future Callable implementation, submit multiple tasks and wait for any one of them to complete
	 */
	public static void invokeAnyExample() throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(5);

        Callable<String> task1 = () -> {
            Thread.sleep(2000);
            return "Result of Task1";
        };
		
        Callable<String> task2 = () -> {
            Thread.sleep(1000);
            return "Result of Task2";
        };

        Callable<String> task3 = () -> {
            Thread.sleep(5000);
            return "Result of Task3";
        };
		
        List<Callable<String>> taskList = Arrays.asList(task1, task2, task3);
        // submitting all the tasks as a List of Callable and get the result of the fastest callable
        System.out.println("Submitting all the tasks for execution...");
        String result = executorService.invokeAny(taskList);
        System.out.println(result);
        
        executorService.shutdown();
	}
	
	
}
