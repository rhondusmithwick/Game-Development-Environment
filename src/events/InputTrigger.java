package events;

import api.IInputSystem;
import api.ILevel;
import javafx.beans.value.ObservableValue;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/***
 * @author cyao42, ani
 *         Authors: Carolyn Yao, Anirudh Jonnavithula
 *         Listeners that notify the event system if a specific key is pressed. 
 */

public abstract class InputTrigger extends Trigger {

	private EventType eventType;
	
	public InputTrigger(EventType eventType) {
		this.eventType = eventType;
	}
	
    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (meetsCriteria(observable, oldValue, newValue)) {
            setChanged();
            notifyObservers();
        }
    }
    
    @Override
    @Deprecated
    public void clearListener(ILevel universe, IInputSystem inputSystem) {
        inputSystem.unListenToInput(this);
    }
    
    @Override
    public void clearListener(ILevel universe) {
        universe.getEventSystem().unListenToInput(this);
    }

    @Override
    @Deprecated
    public void addHandler(ILevel universe, IInputSystem inputSystem) {
        inputSystem.listenToInput(this);
    }
    
    @Override
    public void addHandler(ILevel universe) {
        universe.getEventSystem().listenToInput(this);
    }
    
    public abstract boolean meetsCriteria(ObservableValue observable, Object oldValue, Object newValue);
    
    private void setEventType(EventType eventType) {
    	this.eventType = eventType;
    }
    
    protected EventType getEventType() {
    	return eventType;
    }

}