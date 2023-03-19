package org.gmdev.event.fileservice;

import java.io.File;

public class LogListener implements EventListener {
	
	private final File logFile;
	private final String name;
	private final String subscriberType;
	
    public LogListener(String fileNamePath, String name) {
        this.logFile = new File(fileNamePath);
        this.name = name;
        this.subscriberType = Const.LOG_LISTENER;
    }
	
	@Override
	public void update(File file) {
		// log that the file was uploaded
		System.out.println("Save to log " + logFile + " -> File name : " + file.getName());
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
