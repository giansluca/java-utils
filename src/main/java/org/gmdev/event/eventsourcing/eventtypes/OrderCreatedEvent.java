package org.gmdev.event.eventsourcing.eventtypes;

import org.gmdev.event.eventsourcing.EventStore;

import java.sql.Timestamp;

public class OrderCreatedEvent implements Event {
	
	private final String customerId;
	private final String orderId;
	private final Double orderTotal;
	private final Timestamp eventTime;
	private final String status;
	private String eventStoreId;
	
	public OrderCreatedEvent(String customerId, String orderId, Double orderTotal, Timestamp eventTime) {
		this.customerId = customerId;
		this.orderId = orderId;
		this.orderTotal = orderTotal;
		this.eventTime = eventTime;
		this.status = "ORDER CREATED";
	}
	
	@Override
	public void storeEvent() {
		eventStoreId = new EventStore(customerId, orderId, status, eventTime).persist();
	}
	
	@Override
	public String getCustomerId() {
		return customerId;
	}
	
	@Override
	public String getOrderId() {
		return orderId;
	}
	
	@Override
	public Double getOrderTotal() {
		return orderTotal;
	}
	
	@Override
	public Timestamp getEventTime() {
		return eventTime;
	}

	@Override
	public String getEventStatus() {
		return status;
	}
	
	@Override
	public String getEventStoreId() {
		return eventStoreId;
	}
	
	
}
