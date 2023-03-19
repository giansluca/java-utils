package org.gmdev.event.messageservice;

import java.util.concurrent.TimeUnit;

public class RestSubscriber implements MessageEventSubscriber {

	private final String url;
	
	public RestSubscriber(String url) {
		this.url = url;
	}

	@Override
	public void update(EventMessage message) {
		System.out.println("Sending Rest message to: " + this.url + " - MESSAGE: " + 
				message.getMessage() + " Message Date: " + message.getDate());
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getInfo() {
		return "URL : " + url;
	}
	
	
	
}
