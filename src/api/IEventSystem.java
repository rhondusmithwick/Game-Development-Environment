package api;

import java.io.File;
import java.util.Observer;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import events.Action;
import events.Trigger;
import javafx.scene.input.KeyEvent;
import utility.Pair;

/**
 * Might be scrapped in the future! Reference:
 * http://stackoverflow.com/questions/937302/simple-java-message-dispatching-
 * system
 * 
 * Interface for a global event system based on this model:
 * https://wiki.jmonkeyengine.org/doku.php/jme3:scripting:groovy_event [Trigger:
 * Event(s)+Condition(s)->Result(s)]
 * 
 * @author Tom Wu, ANirudh Jonnavithula, Carolyn Yao
 */
public interface IEventSystem extends Observer, ISerializable {

	// void init(IEntitySystem universe);
	
	/**
	 * Registers a trigger-action pair in the event system
	 * @param trigger - The trigger
	 * @param action - The corresponding action
	 */
	void registerEvent(Trigger trigger, Action action);

	void registerEvent(Pair<Trigger, Action> eventPair);

	void readEventFromFile(String filepath);

	File saveEventsToFile(String filepath);

	void readEventsFromFile(File file);

	String returnEventsAsString();
	
	void setLevel(ILevel level);

	void updateInputs(double dt);

	void takeInput(KeyEvent k);
	
//	void takeMousePress(MouseEvent m);
//	
//	void unListenToMousePress(ChangeListener listener);
//
//	void listenToMousePress(ChangeListener listener);

	void unListenToInput(ChangeListener listener);

	void listenToInput(ChangeListener listener);

	void unListenToTimer(ChangeListener listener);

	void listenToTimer(ChangeListener listener);
	
	String getEventsAsString();

	void setOnInput(Scene scene);
}
