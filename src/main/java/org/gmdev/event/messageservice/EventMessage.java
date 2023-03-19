package org.gmdev.event.messageservice;

import java.time.LocalDate;

public interface EventMessage {
	
	String getMessage();
	
	String getTopic();

	LocalDate getDate();

}
