package org.gmdev.event.eventsourcing.eventsubscribers;

import org.gmdev.event.eventsourcing.eventtypes.Event;

import java.util.concurrent.TimeUnit;

public class CustomerServiceSubscriber implements EventSubscriber {
	
	private final String subscriberName;
	private final String notificationAddress;
	
	public CustomerServiceSubscriber(String notificationAddress) {
		this.notificationAddress = notificationAddress;
		this.subscriberName = "CUSTOMER SERVICE";
	}
	
	@Override
	public void update(Event event) {
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(subscriberName + " notification sent to: " + notificationAddress + " for event: " +
				event.getEventStatus() + " - Customer ID: " + event.getCustomerId() + " - Order ID: " +
				event.getOrderId() + " - Order Total: " + event.getOrderTotal() + " - Time: " + event.getEventTime());
	}
	
	public String getSubscriberName() {
		return subscriberName;
	}
	
	public String getNotificationAddress() {
		return notificationAddress;
	}
	
	

}
