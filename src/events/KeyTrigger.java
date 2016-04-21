package events;

import java.util.Observable;

import api.IComponent;
import api.ILevel;
import api.ILevel;
import api.ISerializable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/***
 * 
 * @author cyao42, ani
 * Authors: Carolyn Yao, Anirudh Jonnavithula
 */

public class KeyTrigger extends Trigger{
	
	private String key; 
	
	public KeyTrigger(String key, ILevel universe, InputSystem inputSystem) {
		this.key = key; 
		addHandler(universe, inputSystem);
	}
	
	@Override
	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
		if (((KeyEvent) newValue).getCode() == KeyCode.getKeyCode(key)) { 
			setChanged();
			notifyObservers();
		}	
	}

	@Override
	public <T extends IComponent> void clearListener(ILevel universe, InputSystem inputSystem) {
		inputSystem.unListenToKeyPress(this);
	}

	@Override
	public <T extends IComponent> void addHandler(ILevel universe, InputSystem inputSystem) {
		inputSystem.listenToKeyPress(this);
	}

	@Override
	public String getID() {
		return key;
	}
}
