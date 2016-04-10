package events;

import api.IEntitySystem;
import javafx.event.Event;
import javafx.scene.input.KeyCode;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rhondusmithwick on 4/10/16.
 *
 * @author Rhondu Smithwick
 */
public class InputSystem  {
    private final Map<KeyCode, Action> actionMap = new HashMap<>();

    public void addEvent(KeyCode k, Action action) {
        actionMap.put(k, action);
    }

    public boolean containsEvent(KeyCode k) {
        return actionMap.containsKey(k);
    }

    public void take(KeyCode k, IEntitySystem universe) {
        if (containsEvent(k)) {
            actionMap.get(k).activate(universe);
        }
    }
}
