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
public class Trigger extends Observable implements ChangeListener, ISerializable{

	private String[] propertySpecifier;
	
	public Trigger(String entityID, IComponent component, int index, IEntitySystem universe) {
		propertySpecifier = new String[3];
		propertySpecifier[0] = entityID;
		propertySpecifier[1] = component.getClass().toString();
		propertySpecifier[2] = index + "";
		universe.getEntity(entityID).getComponent(component.getClass()).getProperties().get(index).addListener(this);
	}
	
	@Override
	public void changed(ObservableValue arg0, Object arg1, Object arg2) {
		setChanged();
		notifyObservers();
	}
	
	public <T extends IComponent> void clearListener(IEntitySystem universe) {
		String entityID = propertySpecifier[0];
		String componentClass = propertySpecifier[1];
		int index = Integer.parseInt(propertySpecifier[2]);
		try {
			universe.getEntity(entityID).getComponent((Class<T>) Class.forName(componentClass)).getProperties().get(index).removeListener(this);
		} catch (ClassNotFoundException e) {
			System.out.println("Could not remove the listener because class was not found.");
		}
	}
}
