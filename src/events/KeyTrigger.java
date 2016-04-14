package events;

import java.util.Observable;

import api.IComponent;
import api.IEntitySystem;
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
	
	public KeyTrigger(String key, InputSystem inputSystem) { 
		this.key = key; 
		inputSystem.listenToKeyPress(this);
	}
	
	@Override
	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
		System.out.println(((KeyEvent)newValue).getCode());
		if (((KeyEvent) newValue).getCode() == KeyCode.getKeyCode(key)) { 
			setChanged();
			notifyObservers();
		}	
	}

	@Override
	public <T extends IComponent> void clearListener(IEntitySystem universe) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T extends IComponent> void addHandler(IEntitySystem universe) {
		// TODO Auto-generated method stub
		
	}
}
