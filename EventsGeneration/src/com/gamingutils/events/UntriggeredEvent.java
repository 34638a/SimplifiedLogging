package com.gamingutils.events;

import java.util.EventObject;

import com.gamingutils.logging.LoggingTag;

public abstract class UntriggeredEvent extends EventObject{
	
	private static final long serialVersionUID = 7329324434556285966L;
	private boolean canceled = false;
	private String[] dataStrings;
	private double[] dataDoubles;
	private static LoggingTag teTag = new LoggingTag("<te>", false, true, true);
	
	public UntriggeredEvent(Object source, String[] dataStrings, double[] dataDoubles) {
		super(source);
		teTag.writeOutTagData("Generating Triggered Event: " + this.getClass().getName());
		this.dataStrings = dataStrings;
		this.dataDoubles = dataDoubles;
	}
	
	public void triggerEvent() {
		teTag.writeOutTagData("Activating Triggered Event: " + this.getClass().getName());
		EventGenerator.generateEvent(this);
	}
	
	public abstract void activateEvent();
	
	public void cancelEvent() {
		this.canceled = true;
	}
	public void reviveEvent() {
		this.canceled = false;
	}
	
	public boolean isCanceled() {
		return this.canceled;
	}
	
	public String[] getDataStrings() {
		return this.dataStrings;
	}
	
	public double[] getDataDoubles() {
		return this.dataDoubles;
	}
}
