package org.gmdev.event.fileservice;

import java.io.File;

public class WebSeviceNotificationListener implements EventListener{
	
	private final String url;
	private final String name;
	private final String subscriberType;
	
	public WebSeviceNotificationListener(String url, String name) {
		this.url = url;
		this.name = name;
		this.subscriberType = Const.WEBSERVICE_NOTIFICATION_LISTENER;
	}
	
	@Override
	public void update(File file) {
		// wait some time ...
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// sending the uploaded file using a Rest WebService
		System.out.println("Sending file to the Web the API " + url + " -> File name : " + file.getName());
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
