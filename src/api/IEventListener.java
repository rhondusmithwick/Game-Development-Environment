package api;

import com.google.common.eventbus.Subscribe;

/**
 * Interface for an event listener/handler
 *
 * @author Tom Wu
 */
public interface IEventListener extends ISerializable {
	
	public void handleEvent();
	
}
