package api;

/**
 * Interface for event notification
 *
 * @author Tom Wu
 */
public interface IEventPublisher extends IEventListener {
	/**
	 * Publishes the event message
	 */
	void publish();

	/**
	 * Gets the Groovy script that determines the event
	 * 
	 * @return the current Groovy script
	 */
	String getScript();

	/**
	 * Sets the Groovy script that determines the event
	 * 
	 * @param groovyScript a new Groovy script
	 */
	void setScript(String groovyScript);
}
