package org.gmdev.concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExample_2 {
	
	
	/**
	 * Example of a remote API
	 */
	private static CompletableFuture<String> userDetail(String userId) {
		return CompletableFuture.supplyAsync(() -> {
			return "gians";
		});	
	}
	
	/**
	 * Example of a remote API
	 */
	private static CompletableFuture<Double> creditRating(String user) {
		return CompletableFuture.supplyAsync(() -> {
			return 8.0;
		});
	}
	
	/**
	 * If your callback function returns a CompletableFuture, and you want a flattened result from 
	 * the CompletableFuture chain (which in most cases you would), then use thenCompose()
	 */
	public static void thenComposeCompletableFuture() throws InterruptedException, ExecutionException {
		CompletableFuture<Double> result = userDetail("5")
				.thenCompose(user -> creditRating(user));

		System.out.println(result.get());
	}
	
	/**
	 * While thenCompose() is used to combine two Futures where one future is dependent on the other, 
	 * thenCombine() is used when you want two Futures to run independently and do something after both are complete.
	 */
	public static void thenCombineCompletableFuture() throws InterruptedException, ExecutionException {
		System.out.println("Retrieving weight.");
		CompletableFuture<Double> weightInKgFuture = CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			
			return 65.0;
		});
	
		System.out.println("Retrieving height.");
		CompletableFuture<Double> heightInCmFuture = CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(4);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			
			return 177.8;
		});
		
		System.out.println("Calculating BMI.");
		CompletableFuture<Double> combinedFuture = weightInKgFuture
		        .thenCombine(heightInCmFuture, (weightInKg, heightInCm) -> {
		    Double heightInMeter = heightInCm/100;
		    
		    return weightInKg / (heightInMeter * heightInMeter);
		});
		
		// The callback function passed to thenCombine() will be called when both the Futures are complete.
		System.out.println("Your BMI is - " + combinedFuture.get());
	}
	
	/**
	 * If an error occurs in the original supplyAsync() task, then none of the thenApply() callbacks 
	 * will be called and future will be resolved with the exception occurred. If an error occurs in first thenApply() 
	 * callback then 2nd and 3rd callback won’t be called and the future will be resolved with the exception occurred, and so on.
	 */
	public static void exceptionCompletableFuture() throws InterruptedException, ExecutionException  {
		Integer age = -1;
		CompletableFuture<String> maturityFuture = CompletableFuture.supplyAsync(() -> {
			if (age < 0) {
				throw new IllegalArgumentException("Age can not be negative");
			}
			
			if (age > 18) {
				return "Adult";
			} else {
				return "Child";
			}
		}).handle((res, ex) -> {
			if (ex != null) {
				System.out.println("Oops! We have an exception - " + ex.getMessage());
				
				return "Unknown!";
			}
			
			return res;
		});

		System.out.println("Maturity : " + maturityFuture.get());
	}
	
	
}
