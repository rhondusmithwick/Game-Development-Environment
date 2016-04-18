package events;

import api.IComponent;
import api.IEntitySystem;
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
	
	public KeyTrigger(String key, IEntitySystem universe, InputSystem inputSystem) { 
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
	public <T extends IComponent> void clearListener(IEntitySystem universe) {
	}

	@Override
	public <T extends IComponent> void addHandler(IEntitySystem universe, InputSystem inputSystem) {
		inputSystem.listenToKeyPress(this);
	}
}
