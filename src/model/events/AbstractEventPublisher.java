package model.events;

import api.IEventPublisher;

/**
 * Publishes an event
 *
 * @author Tom Wu
 */
public abstract class AbstractEventPublisher implements IEventPublisher {
	private String message = "";
	private String groovyScript = "";

}
