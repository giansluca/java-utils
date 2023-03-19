package org.gmdev.event.messageservice;

public interface MessageEventSubscriber {
	
	void update(EventMessage message);
	
	String getInfo();
}
