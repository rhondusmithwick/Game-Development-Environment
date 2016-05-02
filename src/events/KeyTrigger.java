package events;

import javafx.beans.value.ObservableValue;
import javafx.event.EventType;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/***
 * Listeners that notify the event system if a specific key is pressed.
 *
 * @author cyao42, ani
 *         Authors: Carolyn Yao, Anirudh Jonnavithula
 */

public class KeyTrigger extends InputTrigger {

    private KeyCode keyCode;

    public KeyTrigger (String key, EventType eventType) {
        super(eventType);
        setKeyCode(KeyCode.getKeyCode(key));
    }

    public KeyTrigger (String key) {
        this(key, KeyEvent.KEY_PRESSED);
    }

    @Override
    public boolean meetsCriteria (ObservableValue observable, Object oldValue,
                                  Object newValue) {
        if (getEventType() == ((InputEvent) newValue).getEventType()) {
            KeyEvent key = (KeyEvent) newValue;
            if (key.getCode() == getKeyCode()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString () {
        //return getKeyCode().toString()+":"+getEventType().toString();
        return getKeyCode().toString();
    }

    protected KeyCode getKeyCode () {
        return keyCode;
    }

    private void setKeyCode (KeyCode key) {
        this.keyCode = key;
    }
}
