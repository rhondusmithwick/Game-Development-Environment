package api;

/**
 * Interface for event/condition clause of a trigger.
 *
 * @author Tom Wu
 */
public interface IEvent extends ISerializable {

	/**
	 * Gets the message id of an event
	 * 
	 * @return the message id of an event
	 */
	String getEventID();
}
