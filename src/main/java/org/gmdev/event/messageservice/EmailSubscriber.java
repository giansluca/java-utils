package org.gmdev.event.messageservice;

import java.util.concurrent.TimeUnit;

public class EmailSubscriber implements MessageEventSubscriber {
	
	private final String emailAddress;
	
	public EmailSubscriber(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	@Override
	public void update(EventMessage message) {
		System.out.println("Sending Email to: " + this.emailAddress + " - MESSAGE: " + 
				message.getMessage() + " Message Date: " + message.getDate());
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getInfo() {
		return "Email address: " + emailAddress;
	}

}
