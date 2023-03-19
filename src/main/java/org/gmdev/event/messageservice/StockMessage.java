package org.gmdev.event.messageservice;

import java.time.LocalDate;

public class StockMessage implements EventMessage {
	
	private final String message;
	private final String topic;
	private final LocalDate date;
	
	/**
	 * Constructor
	 * Its creates the message date when the object is created (it is created when the message is received)
	 */
	public StockMessage(String message) {
		this.message = message;
		// --> the class name is the key in the topic types Map
		this.topic = FactoryUtil.getStockTopic();
		this.date = LocalDate.now();
	}
	
	@Override
	public String getMessage() {
		// some logic to eventually provide the message in the expected form for the type STOCK
		return message + " - rating 10";
	}

	@Override
	public String getTopic() {
		return topic;
	}
	
	@Override
	public LocalDate getDate() {
		return date;
	}
	
	

}
