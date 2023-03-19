package org.gmdev.event.eventsourcing.eventtypes;

import java.sql.Timestamp;

public interface Event {
	
	void storeEvent();
	
	String getCustomerId();
	
	String getOrderId();
	
	Double getOrderTotal();
	
	Timestamp getEventTime();
	
	String getEventStatus();
	
	String getEventStoreId();
	
}
