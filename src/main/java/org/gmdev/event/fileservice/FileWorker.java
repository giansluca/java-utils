package org.gmdev.event.fileservice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FileWorker {
	
	private static final String PRODUCER_FOLDER = "/Users/gians/Desktop/temp/producerFolder";
	private static final String CONSUMER_FOLDER = "/Users/gians/Desktop/temp/consumerFolder";
	
	/**
	 * create a random file
	 */
	public static void writeRandomFile() {
		String data = "Example File Data";
		String extension = ".txt";
		int nameFielength = 8;
		String charLower = "abcdefghijklmnopqrstuvwxyz";
	    String numbers = "0123456789";
	    String allChars = charLower+numbers;
	    
	    // generate random file name
	    SecureRandom random = new SecureRandom();
	    StringBuilder sb = new StringBuilder(nameFielength);
        
	    for (int i = 0; i < nameFielength; i++) {
        	int rndCharAt = random.nextInt(allChars.length());
            char rndChar = allChars.charAt(rndCharAt);
            sb.append(rndChar);
        }
		
	    // write file
        String fileName = sb.toString() + extension;
		
        try {
			Files.write(Paths.get(PRODUCER_FOLDER + File.separator + fileName), data.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * gets all new files uploaded and moved from PRODUCER_FOLDER to CONSUMER_FOLDER
	 */
	public static File[] getNewFiles() {
		File[] uploadedFilesArray = getFileFromProducer();
		List<File> filterdFilesArray = filterUploadedFiles(uploadedFilesArray);
		File[] movedFiles = moveFilesToConsumer(filterdFilesArray); 
		
		return movedFiles;
	}
	
	/**
	 * gets all files from the PRODUCER_FOLDER
	 */
	private static File[] getFileFromProducer() {
		File[] uploadedFilesArray = null;
		// checking for new files
		File upDirFile = new File(PRODUCER_FOLDER);
		
		if (upDirFile.exists()) {
			// get all files in the directory
			uploadedFilesArray = upDirFile.listFiles();
		}
		
		return uploadedFilesArray;
	}
	
	/**
	 * removing the .DS_Store file to avoid Osx issues
	 */
	private static List<File> filterUploadedFiles(File[] uploadedFilesArray) {
		List<File> uploadedFilesClean = new ArrayList<File>(Arrays.asList(uploadedFilesArray));
		Iterator<File> ite = uploadedFilesClean.iterator();
		
		while (ite.hasNext()) {
			if (ite.next().getName().equals(".DS_Store")) {
				ite.remove();
			}
		}
		
		return uploadedFilesClean;
	}
	
	/**
	 * move the filtered file list from the Producer dir to the Consumer dir
	 */
	private static File[] moveFilesToConsumer(List<File> filterdFilesArray) {
		File[] movedFileArray = null;
		
		if (filterdFilesArray != null && filterdFilesArray.size() > 0) {
			movedFileArray = new File[filterdFilesArray.size()];
			
			try {
				for (int i = 0; i < filterdFilesArray.size(); i++) {
					Path path = Files.move(Paths.get(filterdFilesArray.get(i).getPath()), 
							Paths.get(CONSUMER_FOLDER + File.separator + filterdFilesArray.get(i).getName()));
					movedFileArray[i] = path.toFile();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
		
		return movedFileArray;
	}
	
	
	
}
