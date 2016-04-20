package events;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import utility.Pair;
import api.IEntitySystem;
import api.IEventSystem;
import datamanagement.XMLReader;
import datamanagement.XMLWriter;

/***
 * Created by ajonnav 04/12/16
 * 
 * @author Anirudh Jonnavithula, Carolyn Yao For non-key events, we want to
 *         create a string "entityid:componentName:index". Register string to an
 *         action in the map. A Trigger Factory can interpret the string to
 *         create the right kind of Trigger Using this string, we generate the
 *         triggers in some sort of factory fashion
 *
 */
public class EventSystem implements Observer, IEventSystem {

	private IEntitySystem universe;
	private InputSystem inputSystem;
	private Map<Trigger, Action> actionMap = new HashMap<>();

	public EventSystem(IEntitySystem universe, InputSystem inputSystem) {
		this.universe = universe;
		this.inputSystem = inputSystem;
	}

	public void registerEvent(Trigger trigger, Action action) {
		actionMap.put(trigger, action);
		trigger.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		Action action = actionMap.get(((Trigger) o));
		action.activate(universe);
	}
	
	public File saveEventsToFile(String filepath) {
		stopObservingTriggers(actionMap);
		new XMLWriter<Pair<Trigger,Action>>().writeToFile(filepath, convertMapToList(actionMap));
		watchTriggers(actionMap);
		return new File(filepath);
	}

	public void readEventsFromFilePath(String filepath) {
		List<Pair<Trigger,Action>> eventList= new XMLReader<Pair<Trigger,Action>>().readFromFile(filepath);
		actionMap = convertListToMap(eventList);
		watchTriggers(actionMap);
	}
	
	public void readEventsFromFile(File file) {
		List<Pair<Trigger,Action>> eventList= new XMLReader<Pair<Trigger,Action>>().readFromFile(file.getPath());
		actionMap = convertListToMap(eventList);
		watchTriggers(actionMap);
	}
	
	public String returnEventsAsString() {
		return new XMLWriter<Pair<Trigger,Action>>().writeToString(convertMapToList(actionMap));
	}

	private Map<Trigger, Action> convertListToMap(List<Pair<Trigger,Action>> eventList) {
		Map<Trigger, Action> returnMap = new HashMap<>();
		for (Pair<Trigger,Action> event : eventList) {
			returnMap.put(event.getTrigger(), event.getAction());
			event.getTrigger().addHandler(universe, inputSystem);
		}
		return returnMap;
	}

	private List<Pair<Trigger,Action>> convertMapToList(Map<Trigger, Action> map) {
		List<Pair<Trigger,Action>> returnList = new ArrayList<>();
		for (Trigger trigger : map.keySet()) {
			returnList.add(new Pair<Trigger,Action>(trigger, map.get(trigger)));
		}
		return returnList;
	}

	private void stopObservingTriggers(Map<Trigger, Action> map) {
		for (Trigger trigger : map.keySet()) {
			trigger.deleteObserver(this);
		}
	}

	private void watchTriggers(Map<Trigger, Action> map) {
		for (Trigger trigger : map.keySet()) {
			trigger.addObserver(this);
		}
	}

}
