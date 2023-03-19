package org.gmdev.event.fileservice;

import java.io.File;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FileServiceManager {
	
	private final EventManager eventManager;

	public FileServiceManager() {
		eventManager = EventManager.getInstance();
	}
	
	public void start() {
		// register subscribers
		registerSubscribers();
		// start file service
		startTasks();
	}
	
	private void startTasks() {
		BlockingQueue<File> blockingQueue = new LinkedBlockingDeque<>(10);
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		
		// The producer task - creates files in the producer folder (it simulate an external upload)
		Runnable producerTask = () -> {
			while (true) {
				try {
					for (int i = 0; i < 4; i++) {
						FileWorker.writeRandomFile();
					}
					
					// wait 10 second for next execution
					TimeUnit.SECONDS.sleep(10);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		// The Exchange task - business logic between Producer and Consumer: 
		// gets new uploaded files, moves them to the Consumer directory and put them in the queue
		Runnable exchangeTask = () -> {
			while (true) {
				try {
					// looking and moving for new files in producerFolder to consumerFolder
					File[] newFiles = FileWorker.getNewFiles();
					
					if (newFiles != null && newFiles.length > 0)  {
						for (File file : newFiles) {
							// putting new files on the queue
							blockingQueue.put(file);
						}
						System.out.println(newFiles.length + " new files put on queue");
					} else {
						System.out.println("No new files have been found");
					}
					
					// wait 6 second for next execution
					TimeUnit.SECONDS.sleep(6);
				}
				catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		// The Consumer task - gets files from the queue (moved in the Consumer directory) 
		// and notify all the subscribers
		Runnable consumerTask = () -> {
			while (true) {	
				try {			
					File file = blockingQueue.take();	
					// notify all the subscriber
					List<Future<String>> futures = eventManager.notify(file);
					
					// wait for all the tasks get done
					for (Future<String> future : futures) {
						// block until the result is available
						String futureMessage = future.get(10, TimeUnit.SECONDS);
						System.out.println(futureMessage);
					}
					
					System.out.println("Notification process finished for file: " + file.getName());
					// wait 10 second for next execution
					TimeUnit.SECONDS.sleep(2);
				} catch(InterruptedException | ExecutionException | TimeoutException e) {
					e.printStackTrace();
				}
			}	
		};
		System.out.println("Starting tasks ...");
		executorService.execute(producerTask);
		executorService.execute(exchangeTask);
		executorService.execute(consumerTask);
	}
	
	/**
	 * Register all the subscribers
	 */
	private void registerSubscribers() {
		EmailNotificationListener mail_sub_a = new EmailNotificationListener("sub_a@example.com", "Max");
		EmailNotificationListener mail_sub_b = new EmailNotificationListener("sub_b@example.com", "Brian");
		EmailNotificationListener mail_sub_c = new EmailNotificationListener("sub_c@example.com", "Tom");
		
		WebSeviceNotificationListener web_sub_a = new WebSeviceNotificationListener("www-11111", "New York");
		WebSeviceNotificationListener web_sub_b = new WebSeviceNotificationListener("www-22222", "Berlin");
		
		LogListener log_sub = new LogListener("/Users/gians/Desktop/file.txt", "MainLog");
		
		eventManager.subscribe(mail_sub_a);
		eventManager.subscribe(mail_sub_b);
		eventManager.subscribe(mail_sub_c);
		eventManager.subscribe(web_sub_a);
		eventManager.subscribe(web_sub_b);
		eventManager.subscribe(log_sub);
	}
	
	
}
