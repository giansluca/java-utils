package org.gmdev.event.fileservice;

import java.io.File;

public interface EventListener {
	
	void update(File file);
	
	String getType();
	
	String getName();

}
