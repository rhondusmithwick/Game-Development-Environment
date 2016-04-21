package events;

import api.IEntitySystem;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/***
 * @author cyao42, ani
 *         Authors: Carolyn Yao, Anirudh Jonnavithula
 */

public class KeyTrigger extends Trigger {

    private String key;

    public KeyTrigger(String key) {
        this.key = key;
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (((KeyEvent) newValue).getCode() == KeyCode.getKeyCode(key)) {
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public void clearListener(IEntitySystem universe, InputSystem inputSystem) {
        inputSystem.unListenToKeyPress(this);
    }

    @Override
    public void addHandler(IEntitySystem universe, InputSystem inputSystem) {
        inputSystem.listenToKeyPress(this);
    }

    /**/
    public String toString() {
        return String.format("%s; %s", getClass().getSimpleName(), key);
    }
}
