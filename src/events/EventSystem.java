package events;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

import utility.SingleProperty;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import api.IEntitySystem;
import javafx.scene.input.KeyCode;

/***
 * Created by ajonnav 04/12/16
 * @author Anirudh Jonnavithula, Carolyn Yao
 * For non-key events, we want to create a string "entityid:componentName:index".
 * Register string to an action in the map. 
 * A Trigger Factory can interpret the string to create the right kind of Trigger
 * Using this string, we generate the triggers in some sort of factory fashion
 *
 */
public class EventSystem implements Observer{
	
	IEntitySystem universe;
	Map<Trigger, Action> actionMap = new HashMap<>();
	
	public EventSystem(IEntitySystem universe) {
		this.universe = universe;
	}
	
	public void registerEvent(Trigger trigger, Action action) {
		actionMap.put(trigger, action);
		trigger.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		Action action = actionMap.get(((Trigger)o));
		action.activate(universe);
	}
	
	
}
