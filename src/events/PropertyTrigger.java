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
public class PropertyTrigger extends Trigger{

	private String entityID;
	private String componentName;
	private int index;
	
	public PropertyTrigger(String entityID, IComponent component, int index, IEntitySystem universe) {
		this.entityID = entityID;
		this.componentName= component.getClass().toString().split(" ")[1];
		this.index = index;
		addHandler(universe);
	}
	
	@Override
	public void changed(ObservableValue arg0, Object arg1, Object arg2) {
		setChanged();
		notifyObservers();
	}
	
	public <T extends IComponent> void clearListener(IEntitySystem universe) {
		try {
			universe.getEntity(entityID).getComponent((Class<T>) Class.forName(componentName)).getProperties().get(index).removeListener(this);
		} catch (ClassNotFoundException e) {
			System.out.println("Could not remove the listener because class was not found.");
		}
	}

	@Override
	public <T extends IComponent> void addHandler(IEntitySystem universe) {
		try {
			universe.getEntity(entityID).getComponent((Class<T>) Class.forName(componentName)).getProperties().get(index).addListener(this);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
