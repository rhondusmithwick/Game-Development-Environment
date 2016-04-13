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

	private static String id;
	
	public Trigger(String entityID, IComponent component, int index, IEntitySystem universe) {
		id=entityID+":"+component.getClass()+":"+index;
		universe.getEntity(entityID).getComponent(component.getClass()).getProperties().get(index).addListener(this);
	}
	
	@Override
	public void changed(ObservableValue arg0, Object arg1, Object arg2) {
		setChanged();
		notifyObservers();
	}
	
	public <T extends IComponent> void clearListener(IEntitySystem universe) {
		String[] descriptors = id.split(":");
		String entityID = descriptors[0];
		String componentClass = descriptors[1];
		int index = Integer.parseInt(descriptors[0]);
		try {
			universe.getEntity(entityID).getComponent((Class<T>) Class.forName(componentClass)).getProperties().get(index).removeListener(this);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
