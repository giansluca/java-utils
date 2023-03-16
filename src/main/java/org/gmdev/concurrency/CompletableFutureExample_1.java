package org.gmdev.concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * CompletableFuture is used for asynchronous programming in Java. 
 * Asynchronous programming is a means of writing non-blocking code by running a task on a separate thread
 * than the main application thread and notifying the main thread about its progress, completion or failure.
 */
public class CompletableFutureExample_1 {
	
	/**
	 * Run some background task asynchronously and don’t want to return anything from the task, 
	 * then you can use CompletableFuture.runAsync()
	 */
	public static void simpleCompletableFuture() throws InterruptedException, ExecutionException {
		// Run a task specified by a Runnable Object asynchronously.
		CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
			@Override
			public void run() {
				// Simulate a long-running Job
				try {
					TimeUnit.SECONDS.sleep(6);
				} catch (InterruptedException e) {
					throw new IllegalStateException(e);
				}
				
				System.out.println("I'll run in a separate thread than the main thread.");
			}
		});
		
		// Block and wait for the future to complete
		future.get();
	}
	
	/**
	 * Same of previous example of CompletableFuture, but using lambda expressions  
	 */
	public static void simpleCompletableFutureLambda() throws InterruptedException, ExecutionException {
		// Using Lambda Expression
		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			// Simulate a long-running Job   
			try {
				TimeUnit.SECONDS.sleep(6);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			
			System.out.println("I'll run in a separate thread than the main thread.");
		});
		
		// Block and wait for the future to complete
		future.get();
	}
	
	/**
	 * Run a task asynchronously and return the result using supplyAsync()
	 */
	public static void returnCompletableFuture() throws InterruptedException, ExecutionException {
		CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
			@Override
			public String get() {
				// Simulate a long-running Job
				try {
					TimeUnit.SECONDS.sleep(6);
				} catch (InterruptedException e) {
					throw new IllegalStateException(e);
				}
				
				return "Result of the asynchronous computation";
			}
		});
		
		// Block and get the result of the Future
		String result = future.get();
		System.out.println(result);
	}
	
	/**
	 * same of previous example of CompletableFuture with return value, but using lambda expressions
	 */
	public static void returnCompletableFutureLambda() throws InterruptedException, ExecutionException {
		// Using Lambda Expression
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			// Simulate a long-running Job
			try {
				TimeUnit.SECONDS.sleep(6);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			
			return "Result of the asynchronous computation";
		});
		
		// Block and get the result of the Future
		String result = future.get();
		System.out.println(result);
	}
	
	/**
	 * CompletableFuture using a thread obtained from your thread pool.
	 */
	public static void poolCompletableFuture() throws InterruptedException, ExecutionException {
		// Variations of runAsync() and supplyAsync() methods
		// CompletableFuture<Void>  runAsync(Runnable runnable)
		// CompletableFuture<Void>  runAsync(Runnable runnable, Executor executor)
		// <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
		// CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)

		ExecutorService executor = Executors.newFixedThreadPool(5);
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			// Simulate a long-running Job
			try {
				TimeUnit.SECONDS.sleep(6);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			
			return "Result of the asynchronous computation";
		}, executor);
		
		// Block and get the result of the Future
		String result = future.get();
		System.out.println(result);
		
		executor.shutdown();
	}
	
	/**
	 * CompletableFuture with callback using thenApply() - transformation and returning value
	 */
	public static void thenApplyCompletableFuture() throws InterruptedException, ExecutionException {
		CompletableFuture<String> whatsYourNameFuture = CompletableFuture.supplyAsync(() -> {
			// Simulate a long-running Job
			try {
				TimeUnit.SECONDS.sleep(4);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			
			return "Gians";
		});
		
		// Attach a callback to the Future using thenApply()
		CompletableFuture<String> greetingFuture = whatsYourNameFuture.thenApply(name -> {
			return "Hello " + name;
		});
		
		// Block and get the result of the future.
		System.out.println(greetingFuture.get());
	}
	
	/**
	 * CompletableFuture with callback chain using thenApply() - chain transformations and returning value
	 */
	public static void thenApplyChainCompletableFuture() throws InterruptedException, ExecutionException {
		CompletableFuture<String> welcomeText = CompletableFuture.supplyAsync(() -> {
			// Simulate a long-running Job
			try {
		        TimeUnit.SECONDS.sleep(4);
		    } catch (InterruptedException e) {
		       throw new IllegalStateException(e);
		    }
			
			System.out.println("First ---");
			
		    return "Gians";
		}).thenApply(name -> {
			System.out.println("Second ---");
			
		    return "Hello " + name;
		}).thenApply(greeting -> {
			System.out.println("Third ---");
			
		    return greeting + ", Welcome to the jungle";
		});
		
		System.out.println(welcomeText.get());
	}
	
	/**
	 * Doesn't return anything from the callback function, just run some piece of code after the completion of the Future
	 * thenAccept() can access the return value of the Future's result
	 */
	public static void thenAcceptCompletableFuture() throws InterruptedException, ExecutionException {
		// Using ExecutorService and creating a ThreadPool we don't have to call the get() method
		// on the CompletableFuture at the end.
		// If we use the default ForkJoinPools we have to call get() at the end for waiting all the tasks are completed,  
		// this will keep the JVM alive. If we don't call the get(), our CompletableFuture will die
		
		ExecutorService executor = Executors.newFixedThreadPool(4);
		CompletableFuture.supplyAsync(() -> {
			// Simulate a long-running Job
			try {
				TimeUnit.SECONDS.sleep(4);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			
			return "YESSS WE CAN";
		}, executor).thenAccept(slogan -> {
			System.out.println("Got slogan from remote service :" + slogan);
		});
		
		System.out.println("Done!");
		// shutDown will be executed after all the tasks are completed
		executor.shutdown();
	}
	
	/**
	 * Doesn't return anything from the callback function, just run some piece of code after the completion of the Future
	 * thenRun() doesn’t have access to the Future’s result.
	 */
	public static void thenRunCompletableFuture() throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(4);
		CompletableFuture.supplyAsync(() -> {
			// Simulate a long-running Job
			try {
				TimeUnit.SECONDS.sleep(4);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			
			System.out.println("Inside supplyAsync()");
			
			return "supplyAsync Done!";
		}, executor).thenRun(() -> {
			System.out.println("Inside thenRun()");
		});
		
		executor.shutdown();
	}
	
	/**
	 * If you use thenApplyAsync() callback, then it will be executed in a different thread, 
	 * The second thread will be from ForkJoinPool if i don't use Executor on thenApplyAsync()
	 * In a chain it will wait the result from the previous future
	 */
	public static void thenApplyAsyncCompletableFuture() throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(4);
		CompletableFuture.supplyAsync(() -> {
			// Simulate a long-running Job
			try {
				System.out.println(Thread.currentThread().getName());
				TimeUnit.SECONDS.sleep(4);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			
			String slogan = "YESSS WE CAN";
			System.out.println(slogan);
			
			return slogan;
		}, executor).thenApplyAsync(slogan -> {
			System.out.println(Thread.currentThread().getName());
			System.out.println(slogan + "  --> DO IT!");
			
			return slogan + "  --> DO IT!";
		});
		
		System.out.println("Done!");
		//executor.shutdown();
	}	
		
	
}
