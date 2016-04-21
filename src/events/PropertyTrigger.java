package events;

import api.IComponent;
import api.IEntity;
import api.IEntitySystem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

/***
 * @author Anirudh Jonnavithula, Carolyn Yao Implements a ChangeListener that
 *         listens to change in a SimpleObjectProperty for now, signals
 *         EventSystem potential subclasses: PropertyTrigger, KeyTrigger,
 *         CollisionTrigger?, OtherEventTrigger
 */
public class PropertyTrigger extends Trigger {

	private final String entityID;
	private final Class<? extends IComponent> componentClass;
	private final String propertyName;

	public PropertyTrigger(String entityID, Class<? extends IComponent> componentClass, String propertyName,
			IEntitySystem universe, InputSystem inputSystem) {
		this.entityID = entityID;
		this.componentClass = componentClass;
		this.propertyName = propertyName;
		// addHandler(universe, inputSystem);
	}

	@Override
	public void changed(ObservableValue arg0, Object arg1, Object arg2) {
		setChanged();
		notifyObservers();
	}

	@Override
	public void clearListener(IEntitySystem universe, InputSystem inputSystem) {
		getProperty(universe).removeListener(this);

	}

	@Override
	public void addHandler(IEntitySystem universe, InputSystem inputSystem) {
		getProperty(universe).addListener(this);
	}

	private SimpleObjectProperty<?> getProperty(IEntitySystem universe) {
		IEntity entity = universe.getEntity(entityID);
		IComponent component = entity.getComponent(componentClass);
		Class<?> propertyClass = component.getPropertyNamesAndClasses().get(propertyName);
		return entity.getComponent(componentClass).getProperty(propertyClass, propertyName);
	}

	@Override
	public String toString() {
		return String.format("%s; %s; %s; %s", getClass().getSimpleName(), entityID, componentClass.getSimpleName(),
				propertyName);
	}
}
