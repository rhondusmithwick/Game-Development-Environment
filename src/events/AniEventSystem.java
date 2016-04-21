/*package events;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.input.KeyEvent;
import utility.Pair;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import api.IEntitySystem;
import api.IEventSystem;

public class AniEventSystem implements Observer, IEventSystem {
	
	private IEntitySystem universe;
	private InputSystem inputSystem;
	private ListMultimap<Trigger, Action> eventMap = ArrayListMultimap.create();
	
	public AniEventSystem(IEntitySystem universe) {
		this.universe = universe;
		inputSystem = new InputSystem(universe);
	}
	
	public void registerEvent(List<String> triggerDescription, String scriptPath ) {
		Pair<Trigger, Action> event = ActionFactory.createEvent(triggerDescription, scriptPath, universe);
		registerEvent(event._1(), event._2());
	}

	@Override
	public void registerEvent(Trigger trigger, Action action) {
		eventMap.put(trigger, action);
		if (!eventMap.containsKey(trigger)) {
            trigger.addObserver(this);
            trigger.addHandler(universe, inputSystem);
        }
	}
	
	@Override
	public void updateInputs() {
		this.inputSystem.processInputs();
	}

	@Override
	public void takeInput(KeyEvent k) {
		this.inputSystem.takeInput(k);
	}

	@Override
	public void update(Observable o, Object arg) {
		List<Action> actions = eventMap.get((Trigger) o);
        actions.stream().forEach(e -> e.activate(universe));
	}
	
	public void setUniverse(IEntitySystem universe) {
        this.universe = universe;
        addHandlers();
    }
	
	private void unbindEvents() {
		stopObservingTriggers(eventMap);
		clearListeners();
	}

	private void bindEvents() {
		watchTriggers(eventMap);
		addHandlers();
	}
	
	private void stopObservingTriggers(ListMultimap<Trigger, Action> map) {
        for (Trigger trigger : map.keySet()) {
            trigger.deleteObserver(this);
        }
    }

    private void watchTriggers(ListMultimap<Trigger, Action> map) {
        for (Trigger trigger : map.keySet()) {
            trigger.addObserver(this);
        }
    }

    private void addHandlers() {
    	eventMap.keySet().stream().forEach(trigger -> trigger.addHandler(universe, inputSystem));
    }

    private void clearListeners() {
    	eventMap.keySet().stream().forEach(trigger -> trigger.clearListener(universe, inputSystem));
    }

    private void writeObject(ObjectOutputStream out)
            throws IOException {
        clearListeners();
        stopObservingTriggers(eventMap);
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        watchTriggers(eventMap);
    }

	@Override
	public void readEventsFromFilePath(String filepath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public File saveEventsToFile(String filepath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readEventsFromFile(File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String returnEventsAsString() {
		// TODO Auto-generated method stub
		return null;
	}
	
}*/
