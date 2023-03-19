package org.gmdev.event.eventsourcing;

import java.sql.Timestamp;
import java.util.Random;

/**
 * The Entity bean to persist on the Event store Table
 * @author gians
 */
public class EventStore {
	
	private String eventId;
	private String customertId;
	private String orderId;
	private String eventType;
	private Timestamp eventTime;
	
	public EventStore(String customertId, String orderId, String eventType, Timestamp eventTime) {
        this.customertId = customertId;
        this.orderId = orderId;
        this.eventType = eventType;
        this.eventTime = eventTime;
    }
	
	/**
	 * Save The Order Event on the EventStore table
	 */
	public String persist() {
		// DB simulation generating a new event ID
		Random r = new Random();
		int low = 1;
		int high = 1000;
		
		eventId = Integer.toString(r.nextInt(high-low) + low);
		
		System.out.println("Save Order on EventStore => Event ID: " + eventId + " - customertId: "+ customertId +
				" orderId: " + orderId + " eventType: " + eventType + " eventTime: " + eventTime);
		
		return eventId;
	}
 

}
