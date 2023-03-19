package org.gmdev.event.eventsourcing;

import java.sql.Timestamp;
import java.time.Instant;

import org.gmdev.event.eventsourcing.eventtypes.Event;
import org.gmdev.event.eventsourcing.eventtypes.OrderCreatedEvent;
import org.gmdev.event.eventsourcing.eventtypes.OrderShippedEvent;

public class OrderManager {

	public Event createOrderCommand() {
		// Command simulation - new order Created, and get back Order info 
		String customerId = "12348";
		String orderId = "999";
		Double orderTotal = 100.60;
		Timestamp orderTime = Timestamp.from(Instant.now());
		
		System.out.println("Order Created => Order ID: " + orderId);
		
		// create the associated Event
		OrderCreatedEvent orderCreateEvent = new OrderCreatedEvent(customerId, orderId, orderTotal, orderTime);
		
		// store the event on DB Event Table
		orderCreateEvent.storeEvent();
		
		// return the event for notification
		return orderCreateEvent;
	}

	public Event shipOrderCommand() {
		// Command simulation - order Shipped, and get back Order info 
		String customerId = "12348";
		String orderId = "999";
		Double orderTotal = 100.60;
		Timestamp shipTime = Timestamp.from(Instant.now());
		
		System.out.println("Order Shipped => Order ID: " + orderId);
		
		// create the associated Event
		OrderShippedEvent orderShipEvent = new OrderShippedEvent(customerId, orderId, orderTotal, shipTime);
		
		// store the event on DB Event Table
		orderShipEvent.storeEvent();
		
		return orderShipEvent;
	}
	
	
}
