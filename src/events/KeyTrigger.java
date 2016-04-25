package events;

import api.ILevel;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.List;
import java.util.Map;

/***
 * @author cyao42, ani
 *         Authors: Carolyn Yao, Anirudh Jonnavithula
 *         Listeners that notify the event system if a specific key is pressed. 
 */

public class KeyTrigger extends Trigger {

    private String key;

    public KeyTrigger(String key) {
        this.key = key;
    }

    public KeyTrigger(Map<String, String> triggerMapDescription) {
        key = triggerMapDescription.get("key");
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (((KeyEvent) newValue).getCode() == KeyCode.getKeyCode(key)) {
            setChanged();
            notifyObservers();
        }
    }

    @Override
    @Deprecated
    public void clearListener(ILevel universe, InputSystem inputSystem) {
        inputSystem.unListenToKeyPress(this);
    }
    
    @Override
    public void clearListener(ILevel universe) {
        universe.getEventSystem().unListenToKeyPress(this);
    }

    @Override
    @Deprecated
    public void addHandler(ILevel universe, InputSystem inputSystem) {
        inputSystem.listenToKeyPress(this);
    }
    
    @Override
    public void addHandler(ILevel universe) {
        universe.getEventSystem().listenToKeyPress(this);
    }

    /**/
    public String toString() {
        return String.format("%s; %s", getClass().getSimpleName(), key);
    }
}
