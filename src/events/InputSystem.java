package events;

import api.IEntitySystem;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rhondusmithwick on 4/10/16.
 *
 * @author Rhondu Smithwick
 */
public class InputSystem {
    private final Map<String, Action> actionMap = new HashMap<>();
    private final Map<KeyCode, String> keyMap = new HashMap<>();
    private IEntitySystem universe;

    public InputSystem(IEntitySystem universe) {
        this.universe = universe;
    }

    public void addEvent(String actionName, Action action) {
        actionMap.put(actionName, action);
    }
    
    public void addKeyBinding(KeyCode k, String actionName) {
    	keyMap.put(k, actionName);
    }

    public boolean containsEvent(String actionName) {
        return actionMap.containsKey(actionName);
    }
    
    public boolean containsKey(KeyCode k) {
        return keyMap.containsKey(k);
    }

    public void take(KeyCode k) {
    	// TODO maybe change this if tree
        if (containsKey(k)) {
        	String actionName = keyMap.get(k);
        	if(containsEvent(actionName)) {
        		actionMap.get(keyMap.get(k)).activate(universe);
        	}
        }
    }

    public void setUniverse(IEntitySystem universe) {
        this.universe = universe;
    }

}
