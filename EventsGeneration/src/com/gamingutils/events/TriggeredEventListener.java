package com.gamingutils.events;

import java.util.EventListener;

/**
 * The listener interface for receiving triggeredEvent events.
 * The class that is interested in processing a triggeredEvent
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addTriggeredEventListener<code> method. When
 * the triggeredEvent event occurs, that object's appropriate
 * method is invoked.
 *
 * @see TriggeredEventEvent
 */
public interface TriggeredEventListener extends EventListener{
	
	/**
	 * Process and Event, Triggered or not.
	 *
	 * @param event the event
	 */
	public void processEvent(UntriggeredEvent event);
}
