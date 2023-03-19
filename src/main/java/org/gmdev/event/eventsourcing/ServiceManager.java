package org.gmdev.event.eventsourcing;

import org.gmdev.event.eventsourcing.eventsubscribers.CustomerServiceSubscriber;
import org.gmdev.event.eventsourcing.eventtypes.Event;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ServiceManager {
	
	EventManager eventManagerInstance;
	OrderManager orderManager;
	
	public ServiceManager() {
		eventManagerInstance = EventManager.getInstance();
		orderManager = new OrderManager();
	}

	public void startService() {
		registerSubscribers();
		createOrderService();
		
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		shipOrderService();
	}

	private void createOrderService() {
		// Create order, Store Event and return a new Event ORDER CREATED
		Event createdEvent = orderManager.createOrderCommand();
		// Event sending notification to all the subscribers
		List<Future<String>> futureList = eventManagerInstance.notifySubscribers(createdEvent);
		
		for (Future<String> future : futureList) {
			try {
				// wait for all the notifications have been sent
				future.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	private void shipOrderService() {
		// Ship order, Store Event and return a new Event ORDER SHIPPED
		Event shipEvent = orderManager.shipOrderCommand();
		// Event sending notification to all the subscribers
		List<Future<String>> futureList = eventManagerInstance.notifySubscribers(shipEvent);
		
		for (Future<String> future : futureList) {
			try {
				// wait for all the notifications have been sent
				future.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	private void registerSubscribers() {
		eventManagerInstance.subscribe(new CustomerServiceSubscriber("customer@abc.com"));
		eventManagerInstance.subscribe(new CustomerServiceSubscriber("bigOne@abc.com"));
	}

}
