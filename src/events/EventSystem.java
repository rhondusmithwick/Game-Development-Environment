package events;

import api.IEntitySystem;
import api.IEventSystem;
import api.ILevel;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import datamanagement.XMLReader;
import datamanagement.XMLWriter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.input.KeyEvent;
import utility.Pair;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/***
 * Created by ajonnav 04/12/16
 *
 * @author Anirudh Jonnavithula, Carolyn Yao For non-key events, we want to
 *         write a string that denotes which entity and which property to watch. 
 *         For a key event, we ask the inputSystem to listen, and write the 
 *         key character to file to write to data. 
 *         We also write to file the Action that corresponds to the property change or 
 *         key event. When we read the file, aka play the game, we read the strings from data files
 *         and create Triggers, which add listeners to said properties or keys. The
 *         Triggers are mapped to Actions in the EventSystem map. 
 */

public class EventSystem implements Observer, IEventSystem {
    private final InputSystem inputSystem = new InputSystem();
    private final EventFactory eventFactory = new EventFactory();
    private transient ILevel level;
    private ListMultimap<Trigger, Action> actionMap = ArrayListMultimap.create();
    private final SimpleDoubleProperty timer = new SimpleDoubleProperty(this, "timer", 0.0);
    private transient ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
    public EventSystem(ILevel level) {
        setLevel(level);
    }

    public void registerEvent(Trigger trigger, Action action) {
    	actionMap.put(trigger, action);
        trigger.addObserver(this);
        trigger.addHandler(level);
    }

    public void createEvent(Map<String, String> triggerDescription, String scriptPath) {
        Pair<Trigger, Action> eventPair = eventFactory.createEvent(triggerDescription, scriptPath);
        registerEvent(eventPair._1(), eventPair._2());
    }

    @Override
    public void updateInputs(double dt) {
        this.inputSystem.processInputs();
        timer.set(timer.get()+dt);
        //System.out.println(timer.get());
    }

    @Override
    public void takeInput(KeyEvent k) {
        this.inputSystem.takeInput(k);
    }
    
    @Override
    public void listenToKeyPress(ChangeListener listener) {
        inputSystem.listenToKeyPress(listener);
    }

    @Override
    public void unListenToKeyPress(ChangeListener listener) {
        inputSystem.unListenToKeyPress(listener);
    }
    
    @Override
    public void listenToTimer(ChangeListener listener) {
		timer.addListener(listener);
	}

    @Override
    public void unListenToTimer(ChangeListener listener) {
    	timer.removeListener(listener);
    }

    @Override
    public void update(Observable o, Object arg) {
        List<Action> actions = actionMap.get((Trigger) o);
        actions.stream().forEach(e -> e.activate(engine, level));
    }

    public void setLevel(ILevel level) {
    	if(!actionMap.isEmpty() && this.level.getEntitySystem()!=null) {
    		this.unbindEvents();
    	}
        this.level = level;
        this.bindEvents();
    }

    @SuppressWarnings("unchecked")
    @Override
    public File saveEventsToFile(String filepath) {
        this.unbindEvents();
        File file = new XMLWriter<ListMultimap<Trigger, Action>>().writeToFile(filepath, actionMap);
        this.bindEvents();
        return file;
    }

    @Override
    public void readEventFromFile(String filepath) {
        this.unbindEvents();
        ListMultimap<Trigger, Action> eventMap = new XMLReader<ListMultimap<Trigger, Action>>()
                .readSingleFromFile(filepath);
        actionMap.putAll(eventMap);
        this.bindEvents();
    }

    @Override
    public void readEventsFromFile(File file) {
       readEventFromFile(file.getPath());
    }

    @SuppressWarnings("unchecked")
    @Override
    public String returnEventsAsString() {
        return new XMLWriter<ListMultimap<Trigger, Action>>().writeToString(actionMap);
    }
    
    /*public List<String> getEventsAsStringList() {
    	return actionMap.keySet().stream().forEach(e-> {
    		String s=e.toString()+";";
    		actionMap.get(e).stream().forEach(e->s+=e.toString);
    	});
    }*/

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
        actionMap.keySet().stream().forEach(trigger -> trigger.addHandler(level));
    }

    private void clearListeners() {
        actionMap.keySet().stream().forEach(trigger -> trigger.clearListener(level));
    }

    private void unbindEvents() {
        stopObservingTriggers(actionMap);
        clearListeners();
    }

    private void bindEvents() {
        watchTriggers(actionMap);
        addHandlers();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        this.unbindEvents();
        out.defaultWriteObject();
        this.bindEvents();
    }
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        engine = new ScriptEngineManager().getEngineByName("groovy");
    }

}
