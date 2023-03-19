package org.gmdev.event.fileservice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EventManager {
	
	private static EventManager eventManager;
	private final List<EventListener> subscribers;
	
	/**
	 * Singleton instance (if i need to get this object from different places, i don't need right now)
	 */
	public static EventManager getInstance() {
		if (eventManager == null) {
			eventManager = new EventManager();;
        }
		
        return eventManager;
	}
	
	/**
	 * Private Constructor for Singleton instance
	 */
	private EventManager() {
		subscribers = new ArrayList<>();
	}
	
	public void subscribe(EventListener listener) {
		subscribers.add(listener);
    }
	
	public void unsubscribe(String eventType, EventListener listener) {
		subscribers.remove(listener);
    }
	
	/**
	 * Calls the update method for each subscriber (list of Even listener) using a ThreadPool managed by ExecutorService.
	 * Sends the list of Futures back to the caller.
	 */
	public List<Future<String>> notify(File file) {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		List<Future<String>> futureList = new ArrayList<>();
        
		for (EventListener listener : subscribers) {
        	Future<String> future = executorService.submit(() -> {
        		listener.update(file);
        		return "--> Notification done for Subscriber: " + listener.getType() + " - " + listener.getName();
        	});
        	
        	futureList.add(future);
        }
		
        return futureList;
    }
	
	
}
