package org.gmdev.event.eventsourcing.eventsubscribers;

import org.gmdev.event.eventsourcing.eventtypes.Event;

public interface EventSubscriber {
	
	void update(Event event);
}
