package events;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
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
		new XMLWriter<EventContainer>().writeToFile(filepath, convertMapToList(actionMap));
		watchTriggers(actionMap);
		return new File(filepath);
	}

	public void readEventsFromFilePath(String filepath) {
		List<EventContainer> eventList= new XMLReader<EventContainer>().readFromFile(filepath);
		actionMap = convertListToMap(eventList);
		watchTriggers(actionMap);
	}
	
	public void readEventsFromFile(File file) {
		List<EventContainer> eventList= new XMLReader<EventContainer>().readFromFile(file.getPath());
		actionMap = convertListToMap(eventList);
		watchTriggers(actionMap);
	}
	
	public String returnEventsAsString() {
		return new XMLWriter<EventContainer>().writeToString(convertMapToList(actionMap));
	}

	private Map<Trigger, Action> convertListToMap(List<EventContainer> eventList) {
		Map<Trigger, Action> returnMap = new HashMap<>();
		for (EventContainer event : eventList) {
			returnMap.put(event.getTrigger(), event.getAction());
			event.getTrigger().addHandler(universe, inputSystem);
		}
		return returnMap;
	}

	private List<EventContainer> convertMapToList(Map<Trigger, Action> map) {
		List<EventContainer> returnList = new ArrayList<>();
		for (Trigger trigger : map.keySet()) {
			returnList.add(new EventContainer(trigger, map.get(trigger)));
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

	@Override
	public void readEventsFromFile(String filepath) {
		// TODO Auto-generated method stub
		
	}

}
