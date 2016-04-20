package events;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

	private transient IEntitySystem universe;
	private InputSystem inputSystem;
	private Map<Trigger, List<Action>> actionMap = new HashMap<>();

	public EventSystem(IEntitySystem universe, InputSystem inputSystem) {
		this.universe = universe;
		this.inputSystem = inputSystem;
	}

	@Override
	public void registerEvent(Trigger trigger, Action action) {
		if(actionMap.containsKey(trigger)) {
			List<Action> tempList = actionMap.get(trigger);
			tempList.add(action);
		}
		else {
			List<Action> newList = new ArrayList<>();
			newList.add(action);
			actionMap.put(trigger, newList);
			trigger.addObserver(this);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		actionMap.get((Trigger)o).stream().forEach(e->e.activate(universe));
	}


	@Override
	public void setUniverse(IEntitySystem universe) {
		this.universe = universe;
		addHandlers();
	}

	@Override
	public File saveEventsToFile(String filepath) {
		stopObservingTriggers(actionMap);
		new XMLWriter<Pair<Trigger, List<Action>>>().writeToFile(filepath, convertMapToList(actionMap));
		watchTriggers(actionMap);
		return new File(filepath);
	}

	@Override
	public void readEventsFromFilePath(String filepath) {
		List<Pair<Trigger, List<Action>>> eventList= new XMLReader<Pair<Trigger, List<Action>>>().readFromFile(filepath);
		actionMap = convertListToMap(eventList);
		watchTriggers(actionMap);
	}

	@Override
	public void readEventsFromFile(File file) {
		List<Pair<Trigger, List<Action>>> eventList= new XMLReader<Pair<Trigger, List<Action>>>().readFromFile(file.getPath());
		actionMap = convertListToMap(eventList);
		watchTriggers(actionMap);
	}

	@Override
	public String returnEventsAsString() {
		return new XMLWriter<Pair<Trigger, List<Action>>>().writeToString(convertMapToList(actionMap));
	}

	private Map<Trigger, List<Action>> convertListToMap(List<Pair<Trigger, List<Action>>> eventList) {
		Map<Trigger, List<Action>> returnMap = new HashMap<>();
		for (Pair<Trigger, List<Action>> event : eventList) {
			returnMap.put(event._1(), event._2());
			event._1().addHandler(universe, inputSystem);
		}
		return returnMap;
	}

	private List<Pair<Trigger, List<Action>>> convertMapToList(Map<Trigger, List<Action>> map) {
		List<Pair<Trigger, List<Action>>> returnList = new ArrayList<>();
		for (Trigger trigger : map.keySet()) {
			returnList.add(new Pair<Trigger, List<Action>>(trigger, map.get(trigger)));
		}
		return returnList;
	}

	private void stopObservingTriggers(Map<Trigger, List<Action>> map) {
		for (Trigger trigger : map.keySet()) {
			trigger.deleteObserver(this);
		}
	}

	private void watchTriggers(Map<Trigger, List<Action>> map) {
		for (Trigger trigger : map.keySet()) {
			trigger.addObserver(this);
		}
	}

	private void addHandlers() {
		actionMap.keySet().stream().forEach(trigger -> trigger.addHandler(universe, inputSystem));
	}

	private void clearListeners() {
		actionMap.keySet().stream().forEach(trigger -> trigger.clearListener(universe));
	}

	private void writeObject(ObjectOutputStream out)
			throws IOException {
		clearListeners();
		stopObservingTriggers(actionMap);
		out.defaultWriteObject();
	}

	private void readObject(ObjectInputStream in)
			throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		watchTriggers(actionMap);
	}

}
