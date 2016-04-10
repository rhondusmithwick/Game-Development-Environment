package model.events;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.IEventListener;

@Deprecated
// BASED OFF OF IEventSystem
/*
 * Links EventObjects and IEventListeners Utilized to call the relevant event
 * handlers when given an event object
 */
public class EventSystem {

	private Map<EventObject, List<IEventListener>> eventMap = new HashMap<EventObject, List<IEventListener>>();
	private String message;

	/*
	 * Adds the IEventListener to the list of triggers for the given EventObject
	 */
	public void registerTrigger(EventObject event, IEventListener trigger) {
		if (!eventMap.containsKey(event)) {
			eventMap.put(event, new ArrayList<IEventListener>());
		}
		eventMap.get(event).add(trigger);
	}

	/*
	 * Takes the given event and "deregisters" it so it is no longer considered
	 * for event handling purposes
	 */
	public void deregisterTrigger(EventObject event) {
		eventMap.remove(event);
	}

	/*
	 * Given an event, handles it if there exists a handler for it If no handler
	 * is registered for the event, nothing happens
	 */
	public void handleEvent(EventObject event) {
		if (eventMap.containsKey(event)) {
			for (IEventListener listener : eventMap.get(event)) {
				listener.handleEvent();
			}
			// NEED TO MODIFY HOW MESSAGE IS SET
			this.message = "Event handled";
		}
	}

	/**
	 * Method that gets the most recent message from the event handler Useful
	 * for testing purposes
	 * 
	 * @return last message received from event handler
	 */
	public String getLastMessage() {
		return message;
	}

}
