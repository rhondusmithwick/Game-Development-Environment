package events;

import java.util.Observable;

import api.IComponent;
import api.IEntitySystem;
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
public abstract class Trigger extends Observable implements ChangeListener, ISerializable  {
	
	public abstract <T extends IComponent> void clearListener(IEntitySystem universe, InputSystem inputSystem);

	public abstract <T extends IComponent> void addHandler(IEntitySystem universe, InputSystem inputSystem);
	
	public abstract String getID();
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Trigger)) {
			return false;
		}
		Trigger t = (Trigger)o;
		return t.getID().equals(this.getID());
	}

	
}