package api;

import java.io.File;
import java.util.Observer;


import javafx.beans.value.ChangeListener;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import events.Action;
import events.Trigger;
import javafx.scene.input.KeyEvent;

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

	void readEventFromFile(String filepath);

	File saveEventsToFile(String filepath);

	void readEventsFromFile(File file);

	String returnEventsAsString();
	
	void setUniverse(ILevel universe);

	void updateInputs(double dt);

	void takeInput(KeyEvent k);
	
	void takeMousePress(MouseEvent m);
	
	void unListenToMousePress(ChangeListener listener);

	void listenToMousePress(ChangeListener listener);

	void unListenToKeyPress(ChangeListener listener);

	void listenToKeyPress(ChangeListener listener);

	void unListenToTimer(ChangeListener listener);

	void listenToTimer(ChangeListener listener);

<<<<<<< HEAD
=======
	String getEventsAsString();
>>>>>>> 24eda28e83aea64eb86f246e17d170aa6b08d8c5
}
