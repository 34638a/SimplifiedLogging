package com.gamingutils.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Logging {
	
	private static final String filePath = "./log";
	
	private static String LOG_DATA = "";
	private static boolean APPEND_TIMESTAMP = false;
	private static List<LoggingTag> tags = new ArrayList<LoggingTag>();
	//0 = directFileStructure
	//1 = perTagFileStructure
	//2 = perSaveDateFileStructure
	//3 = perSaveDateFileStructure + perTagFileStructure
	private static int FILE_STRUCTURE = 0;
	
	public static void enableLogger() {
		registerLogger(0, true);
	}
	public static void enableLogger(boolean APPEND_TIMESTAMP) {
		registerLogger(0, APPEND_TIMESTAMP);
	}
	public static void enableLogger(int FILE_STRUCTURE) {
		registerLogger(FILE_STRUCTURE, true);
	}
	public static void enableLogger(int FILE_STRUCTURE, boolean APPEND_TIMESTAMP) {
		registerLogger(FILE_STRUCTURE, APPEND_TIMESTAMP);
	}
	
	private static void registerLogger(int fileStructure, boolean timestamp) {
		APPEND_TIMESTAMP = timestamp;
		FILE_STRUCTURE = fileStructure;
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run() {
				writeOutLogToFile(LOG_DATA, filePath + getFileStructure("logMain") + "logMain", ".txt", true);
				//System.out.println(tags.size());
				for (LoggingTag tag : tags) {
					
					if (tag.shouldOutputToOwnLogOnClose()) {
						writeOutLogToFile(tag.getLog(), filePath + getFileStructure(tag.getOutputName()) + tag.getOutputName(), ".txt", true);
					}
				}
			}
		});
	}
	
	private static String getFileStructure(String pathName) {
		String structure = "";
		if (FILE_STRUCTURE == 0) {
			structure = "/";
		} else if (FILE_STRUCTURE == 1) {
			structure = "/" + pathName + "/";
		} else if (FILE_STRUCTURE == 2) {
			structure = "/" + getTimeStamp(true);
			structure = structure.substring(0, structure.length() - 3);
			structure = structure.substring(0,5) + "/" + structure.substring(5,7) + "/" + structure.substring(7,9) + "/";
		} else if (FILE_STRUCTURE == 3) {
			structure = "/" + getTimeStamp(true);
			structure = structure.substring(0, structure.length() - 3);
			structure = structure.substring(0,5) + "/" + structure.substring(5,7) + "/" + structure.substring(7,9) + "/" + pathName + "/";
		}
		return structure;
	}
	
	public static void addTag(LoggingTag tag) {
		tags.add(tag);
	}
	public static void dropTag(LoggingTag tag) {
		tags.remove(tag);
	}
	
	public static void outMessage(String tag, String message, boolean outToConsole) {
		LOG_DATA = appendMessageInLoggingFormat(LOG_DATA, tag, message, APPEND_TIMESTAMP);
		if (outToConsole) {
			System.out.println(getTimeStamp(APPEND_TIMESTAMP) + " | Message: " + message);
		}
	}
	public static void outError(String tag, String message, boolean outToConsole) {
		LOG_DATA = appendMessageInLoggingFormat(LOG_DATA, tag, message, APPEND_TIMESTAMP);
		if (outToConsole) {
			System.err.println(getTimeStamp(APPEND_TIMESTAMP) + " | Message: " + message);
		}
	}
	
	public static String appendMessageInLoggingFormat(String log, String tag, String message, boolean addTimeStamp) {
		return log + "|" + tag + getTimeStamp(addTimeStamp) + message;
	}
	
	private static String getTimeStamp(boolean shouldTimeStamp) {
		String time = "";
		if (shouldTimeStamp) {
			time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(
			        Calendar.getInstance().getTime()) + "<t>";
		}
		return time;
	}
	
	public static void writeOutLogToFile(String logFile, String fileNamePath, String extension, boolean APPEND_TIMESTAMP) {
		String time = getTimeStamp(true);
		time = time.substring(0, time.length() - 3);
		//System.out.println(fileNamePath + time + extension);
		File f = new File(fileNamePath + time + extension);
		f.getParentFile().mkdirs();
		BufferedWriter w = null;
		try {
			w = new BufferedWriter(new FileWriter(f));
			w.write(logFile);
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
