package org.gmdev.event.eventsourcing;

import org.gmdev.event.eventsourcing.eventsubscribers.EventSubscriber;
import org.gmdev.event.eventsourcing.eventtypes.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EventManager {
	
	private static EventManager eventManagerInstance;
	private final List<EventSubscriber> subscribers;
	
	/**
	 * Singleton instance
	 */
	public static EventManager getInstance() {
		if (eventManagerInstance == null) {
			eventManagerInstance = new EventManager();
		}
		
		return eventManagerInstance;
	}
	
	/**
	 * Private constructor
	 */
	private EventManager() {
		subscribers = new ArrayList<>(); 
	}
	
	public void subscribe(EventSubscriber subscriber) {
		subscribers.add(subscriber);
	}
	
	public void unsubscribe(EventSubscriber subscriber) {
		subscribers.remove(subscriber);
	}
	
	public List<Future<String>> notifySubscribers(Event event) {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		List<Future<String>> futureList = new ArrayList<>();
		
		for (EventSubscriber subscriber : subscribers ) {
			Future<String> future = executorService.submit(() -> {
				subscriber.update(event);
				return "Notification sent for event: " + event.getEventStatus();
			});
			
			futureList.add(future);
		}
		
		executorService.shutdown();
		
		return futureList;
	}
	

}
