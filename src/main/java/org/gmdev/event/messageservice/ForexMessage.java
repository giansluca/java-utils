package org.gmdev.event.messageservice;

import java.time.LocalDate;
import java.util.Date;

public class ForexMessage implements EventMessage {
	
	private final String message;
	private final String topic;
	private final LocalDate date;
	
	public ForexMessage(String message) {
		this.message = message;
		// --> the class name is the key in the topic types Map
		this.topic = FactoryUtil.getForexTopic();
		this.date = LocalDate.now();
	}

	@Override
	public String getMessage() {
		// some logic to eventually provide the message in the expected form for the type FOREX
		return this.message+ " - rating  8";
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
