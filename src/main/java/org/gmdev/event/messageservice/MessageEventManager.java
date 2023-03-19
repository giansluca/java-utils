package org.gmdev.event.messageservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MessageEventManager {
	
	private static MessageEventManager eventsManagerInstance;
	private final Map<String, List<MessageEventSubscriber>> subscribers;
	
	/**
	 * Singleton instance (if i need to get this object from different places, i don't need right now)
	 */
	public static MessageEventManager getInstance() {
        if (eventsManagerInstance == null) {
        	eventsManagerInstance = new MessageEventManager();
        }
        
        return eventsManagerInstance;
    }
	
	/**
	 * Private Constructor for Singleton instance
	 */
    private MessageEventManager() {
    	subscribers = new HashMap<>();
		
    	// Populating The Map with Message types as Key -
    	// The message topic list could be pulled from a DB for example
    	List<String> topics = FactoryUtil.getAllTopics();

		for (String topic : topics) {
			subscribers.put(topic, new ArrayList<>());
		}
    }
	
	/**
	 * Add a subscriber for the interested Topics
	 */
	public void subscribe(MessageEventSubscriber subscriber, String...topics) {
		for (String topic : topics) {
			// get the array of subscribers for the message type
			List<MessageEventSubscriber> subcribersByType = subscribers.get(topic);
			
			// add a new subscriber
			subcribersByType.add(subscriber);
		}
	}
	
	/**
	 * Remove a subscriber
	 */
	public void unsubscribe(MessageEventSubscriber subscriber, String messageType) {
		List<MessageEventSubscriber> subscribersByType = subscribers.get(messageType);
		subscribersByType.remove(subscriber);
	}
	
	/**
	 * Send notification to all the subscribers
	 * I made the method synchronized because it is called from two different thread (the two consumer)
	 */
	public synchronized List<Future<String>> notifySubscribers(EventMessage message) {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		List<Future<String>> futureList = new ArrayList<>();
		
		// get the array of subscribers for the message type
		List<MessageEventSubscriber> subscribersByType = subscribers.get(message.getTopic());
		
		for (MessageEventSubscriber subscriber : subscribersByType) {
			Future<String> future = executorService.submit(() -> {
				// send notification
				subscriber.update(message);
				return "Message: " + message.getMessage() + " Sent to: " + subscriber.getInfo();
			});
			
			// add the future to the list to return
			futureList.add(future);
		}
		
		return futureList;
	}
	
	
}
