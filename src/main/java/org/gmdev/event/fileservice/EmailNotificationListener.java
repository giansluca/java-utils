package org.gmdev.event.fileservice;

import java.io.File;

public class EmailNotificationListener implements EventListener {
	
	private final String email;
	private final String name;
	private final String subscriberType;
	
    public EmailNotificationListener(String email, String name) {
    	this.email = email;
    	this.name = name;
    	this.subscriberType = Const.EMAIL_NOTIFICATION_LISTENER;
    }	
	
	@Override
	public void update(File file) {
		// wait some time ...
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// sending email with the file attached
		System.out.println("Sending Email to " + email + " -> File name : " + file.getName());
	}

	@Override
	public String getType() {
		return subscriberType;
	}

	@Override
	public String getName() {
		return name;
	}

}
