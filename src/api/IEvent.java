package api;

/**
 * Interface for event/condition clause of a trigger.
 *
 * @author Tom Wu
 */
public interface IEvent extends ISerializable {
	
	/**
	 * Gets the unique id of an event
	 * 
	 * @return the unique id of an event
	 */
	int getEventID();
}
