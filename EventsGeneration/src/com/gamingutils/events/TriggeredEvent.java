package com.gamingutils.events;

public abstract class TriggeredEvent extends UntriggeredEvent {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 517236792999908304L;

	public TriggeredEvent(Object source, String[] dataStrings, double[] dataDoubles) {
		super(source, dataStrings, dataDoubles);
		this.triggerEvent();
	}
}
