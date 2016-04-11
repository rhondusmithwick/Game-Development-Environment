package api;

@Deprecated
/**
 * Interface for event handling
 *
 * @author Tom Wu
 */
public interface IEventSubscriber extends IEventListener {
	/**
	 * Subscribe to an event
	 * 
	 * @param message
	 *            the message id of the event
	 */
	void subscribe(String message);

	/**
	 * Gets the Groovy script that handles the event
	 * 
	 * @return the current Groovy script
	 */
	String getScript();

	/**
	 * Sets the Groovy script that handles the event
	 * 
	 * @param groovyScript
	 *            a new Groovy script
	 */
	void setScript(String groovyScript);
}
