package events;

import java.util.Observable;
import api.ISerializable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
/***
 * 
 * @author Anirudh Jonnavithula, Carolyn Yao
 * Implements a ChangeListener that listens to change in a SimpleObjectProperty for now, signals EventSystem
 * potential subclasses: PropertyTrigger, KeyTrigger, CollisionTrigger?, OtherEventTrigger
 */
public class Trigger extends Observable implements ChangeListener, ISerializable{

	String id;
	
	public Trigger(String enitityID, String componentName, int index, SimpleObjectProperty<?> property) {
		this.property = property;
		this.property.addListener(this);
	}
	
	@Override
	public void changed(ObservableValue arg0, Object arg1, Object arg2) {
		setChanged();
		notifyObservers();
	}
	
	public void clearListener() {
		property.removeListener(this);
	}
}
