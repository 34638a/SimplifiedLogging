package com.gamingutils.events;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import javax.swing.event.EventListenerList;

import com.gamingutils.logging.LoggingTag;

public abstract class EventGenerator {
	
	protected EventListenerList listenersList = new EventListenerList();
	private static LoggingTag egTag = new LoggingTag("<eg>", false, true, true);
	
	
	private static List<EventGenerator> GENERATORS = new ArrayList<EventGenerator>();
	
	/**
	 * Adds an EventListener.
	 *
	 * @param listener the listener
	 */
	protected void addEventListener(EventListener listener) {
		egTag.writeOutTagData("Registering Event Listener: " + listener.getClass().getName());
		listenersList.add(EventListener.class, listener);
	}
	
	/**
	 * Removes an EventListener.
	 *
	 * @param listener the listener
	 */
	protected void removeEventListener(EventListener listener) {
		egTag.writeOutTagData("Removing Event Listener: " + listener.getClass().getName());
		listenersList.remove(EventListener.class, listener);
	}
	
	/**
	 * Fire event. (Private Function)
	 *
	 * @param event the event
	 */
	private void fireEvent(UntriggeredEvent event) {
		Object[] listeners = listenersList.getListenerList();
		for (int i = 0; i < listeners.length; i++) {
			((TriggeredEventListener) listeners[i]).processEvent(event);
		}
	}
	
	/**
	 * Triggered when an Event is made to parse the event onto it's listeners and then have it be validated.
	 *
	 * @param event the event
	 */
	public static void generateEvent(UntriggeredEvent event) {
		for (EventGenerator generator : GENERATORS) {
			generator.fireEvent(event);
		}
		if (!event.isCanceled()) {
			egTag.writeOutTagData("Triggering Event: " + event.getClass().getName());
			event.activateEvent();
		}
	}
}
