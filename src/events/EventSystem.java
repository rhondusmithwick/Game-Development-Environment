package events;

import api.IEventSystem;
import api.ILevel;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import datamanagement.XMLReader;
import datamanagement.XMLWriter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

/***
 * Created by ajonnav 04/12/16
 *
 * @author Anirudh Jonnavithula, Carolyn Yao For non-key events, we want to
 *         create a string "entityid:componentName:index". Register string to an
 *         action in the map. A Trigger Factory can interpret the string to
 *         create the right kind of Trigger Using this string, we generate the
 *         triggers in some sort of factory fashion
 */
public class EventSystem implements Observer, IEventSystem {
    private final InputSystem inputSystem = new InputSystem();
    private transient ILevel universe;
    private ListMultimap<Trigger, Action> actionMap = ArrayListMultimap.create();
    private final SimpleDoubleProperty timer = new SimpleDoubleProperty(this, "timer", 0.0);
    private transient ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
    public EventSystem(ILevel universe) {
        setUniverse(universe);
    }

    @Override
    public void registerEvent(Trigger trigger, Action action) {
    	actionMap.put(trigger, action);
        //if (!actionMap.containsKey(trigger)) {
            trigger.addObserver(this);
            trigger.addHandler(universe);
        //}
        
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
        actions.stream().forEach(e -> e.activate(engine, universe));
    }

    @Override
    public void setUniverse(ILevel universe) {
        this.unbindEvents();
        this.universe = universe;
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
        actionMap.keySet().stream().forEach(trigger -> trigger.addHandler(universe));
    }

    private void clearListeners() {
        actionMap.keySet().stream().forEach(trigger -> trigger.clearListener(universe));
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
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        engine = new ScriptEngineManager().getEngineByName("groovy");
    }

}
