package api;

import java.util.Observer;

import events.Action;
import events.Trigger;

/**
 * Might be scrapped in the future! Reference:
 * http://stackoverflow.com/questions/937302/simple-java-message-dispatching-
 * system
 * 
 * Interface for a global event system based on this model:
 * https://wiki.jmonkeyengine.org/doku.php/jme3:scripting:groovy_event [Trigger:
 * Event(s)+Condition(s)->Result(s)]
 * 
 * @author Tom Wu, ANirudh Jonnavithula
 */
public interface IEventSystem extends Observer, ISerializable {

	// void init(IEntitySystem universe);

	void registerEvent(Trigger trigger, Action action);

	void saveEventsToFile(String filepath);

	void readEventsFromFile(String filepath);

}
