package com.gamingutils.logging;

public class LoggingTag {
	
	private String tag = "";
	private boolean isError = false;
	private boolean outToConsole = false;
	private boolean useTimeStamp = true;
	private boolean outputToOwnLogOnClose = false;
	private String outputName = "";
	private String tagLog = "";
	
	public LoggingTag(String tag) {
		createTag(tag, false, true, false, null);
	}
	public LoggingTag(String tag, boolean isError) {
		createTag(tag, isError, true, false, null);
	}
	public LoggingTag(String tag, boolean isError, boolean useTimeStamp) {
		createTag(tag, isError, useTimeStamp, false, null);
	}
	public LoggingTag(String tag, boolean isError, boolean useTimeStamp, boolean outToConsole) {
		createTag(tag, isError, useTimeStamp, outToConsole, null);
	}
	public LoggingTag(String tag, boolean isError, boolean useTimeStamp, boolean outToConsole, String outputName) {
		createTag(tag, isError, useTimeStamp, outToConsole, outputName);
	}
	
	private void createTag(String tag, boolean isError, boolean useTimeStamp, boolean outToConsole, String outputName) {
		this.tag = tag;
		this.isError = isError;
		this.useTimeStamp = useTimeStamp;
		this.outToConsole = outToConsole;
		this.outputName = outputName;
		if (outputName != null) {
			//System.out.println(outputName);
			this.outputToOwnLogOnClose = true;
		}
		Logging.addTag(this);
	}
	
	public void writeOutTagData(String message) {
		if (this.isError) {
			Logging.outError(tag, message, outToConsole);
		} else {
			Logging.outMessage(tag, message, outToConsole);
		}
		this.tagLog = Logging.appendMessageInLoggingFormat(tagLog, tag, message, useTimeStamp);
	}
	
	public boolean shouldOutputToOwnLogOnClose() {
		return outputToOwnLogOnClose;
	}
	
	public String getLog() {
		return tagLog;
	}
	public String getOutputName() {
		return outputName;
	}
}
