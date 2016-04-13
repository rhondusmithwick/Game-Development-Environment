package events;

import javafx.scene.input.KeyCode;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * Created by rhondusmithwick on 4/10/16.
 *
 * @author Rhondu Smithwick
 */
public class InputSystem {
    private final Map<String, Action> actionMap = new HashMap<>();
    private final Map<KeyCode, String> keyMap = new HashMap<>();

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
        if (containsKey(k)) {
        	String actionName = keyMap.get(k);
        	if(containsEvent(actionName)) {
        		actionMap.get(keyMap.get(k)).activate();
        	}
        }
    }
}
