package api;

import com.google.common.collect.ImmutableBiMap;

/**
 * Might be scrapped in the future! Reference:
 * http://stackoverflow.com/questions/937302/simple-java-message-dispatching-
 * system
 * 
 * Interface for a global event system based on this model:
 * https://wiki.jmonkeyengine.org/doku.php/jme3:scripting:groovy_event [Trigger:
 * Event(s)+Condition(s)->Result(s)]
 * 
 * @author Tom Wu
 */
public interface IEventSystem extends ISerializable {

	// TODO: maps event(s) (including condition(s)) to a listener
	/**
	 * Register a trigger
	 * @param event the event(s) (including condition(s))
	 * @param listener the event listener/handler
	 * @return whether the registration is successful
	 */
	boolean registerTrigger(IEvent event, IEventListener listener);

	/**
	 * De-register a trigger
	 * @param id the event ID
	 * @return whether the de-registration is successful
	 */
	boolean deregisterTrigger(int id);

	/**
	 * Get all trigger(s)
	 * @return immutable bidirectional map of triggers
	 */
	ImmutableBiMap<IEvent, IEventListener> getTriggers();
}
