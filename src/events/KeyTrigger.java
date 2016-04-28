package events;

import api.ILevel;
import javafx.beans.value.ObservableValue;
import javafx.event.EventType;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/***
 * @author cyao42, ani
 *         Authors: Carolyn Yao, Anirudh Jonnavithula
 *         Listeners that notify the event system if a specific key is pressed. 
 */

public class KeyTrigger extends InputTrigger {

    private KeyCode keyCode;

    public KeyTrigger(KeyCode key, EventType eventType) {
    	super(eventType);
        setKeyCode(key);
    }
    
    @Override
	public boolean meetsCriteria(ObservableValue observable, Object oldValue,
			Object newValue) {
		if(getEventType() == ((InputEvent)newValue).getEventType()) {
			KeyEvent key = (KeyEvent)newValue;
			if(key.getCode() == getKeyCode()) {
				return true;
			}
		}
		return false;
	}
    
    @Override
    public String toString() {
    	return getKeyCode().toString()+":"+getEventType().toString();
    }
    
    protected KeyCode getKeyCode() {
    	return keyCode;
    }
    
    private void setKeyCode(KeyCode key) {
    	this.keyCode = key;
    }
}
