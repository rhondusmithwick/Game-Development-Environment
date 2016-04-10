package events;

import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rhondusmithwick on 4/10/16.
 *
 * @author Rhondu Smithwick
 */
public class InputSystem {
    private final Map<KeyCode, Action> actionMap = new HashMap<>();

    public void addEvent(KeyCode k, Action action) {
        actionMap.put(k, action);
    }

    public boolean containsEvent(KeyCode k) {
        return actionMap.containsKey(k);
    }

    public void take(KeyCode k) {
        if (containsEvent(k)) {
            actionMap.get(k).activate();
        }
    }
}
