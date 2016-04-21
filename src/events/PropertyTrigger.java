package events;

import api.IComponent;
import api.IEntity;
import api.ILevel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;


/***
 * @author Anirudh Jonnavithula, Carolyn Yao Implements a ChangeListener that
 *         listens to change in a SimpleObjectProperty.
 */

public class PropertyTrigger extends Trigger {
    private final String entityID;
    private final Class<? extends IComponent> componentClass;
    private final String propertyName;

    public PropertyTrigger(String entityID, Class<? extends IComponent> componentClass, String propertyName) {
        this.entityID = entityID;
        this.componentClass = componentClass;
        this.propertyName = propertyName;
    }

    @Override
    public void changed(ObservableValue arg0, Object arg1, Object arg2) {
        setChanged();
        notifyObservers();
    }

    @Override
    public void clearListener(ILevel universe, InputSystem inputSystem) {
        getProperty(universe).removeListener(this);

    }

    @Override
    public void addHandler(ILevel universe, InputSystem inputSystem) {
        getProperty(universe).addListener(this);
    }

    private SimpleObjectProperty<?> getProperty(ILevel universe) {
        IEntity entity = universe.getEntity(entityID);
        IComponent component = entity.getComponent(componentClass);
        return component.getProperty(propertyName);
    }

    @Override
    public String toString() {
        return String.format("%s; %s; %s; %s", getClass().getSimpleName(), entityID, componentClass.getSimpleName(),
                propertyName);
    }

}
