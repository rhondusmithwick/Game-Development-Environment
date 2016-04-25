package events;

import api.IComponent;
import api.IEntity;
import api.ILevel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

import java.util.List;


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

    public PropertyTrigger(List<String> propertyDescription) {
        entityID = propertyDescription.get(0);
        try {
            componentClass = (Class<? extends IComponent>) Class.forName(propertyDescription.get(1));
        } catch(ClassNotFoundException e) {
        }
        propertyName = propertyDescription.get(2);
    }
    
    @Override
    public void changed(ObservableValue arg0, Object arg1, Object arg2) {
        setChanged();
        notifyObservers();
    }

    @Override
    @Deprecated
    public void clearListener(ILevel universe, InputSystem inputSystem) {
        getProperty(universe).removeListener(this);
    }
    
    @Override
    public void clearListener(ILevel universe) {
        getProperty(universe).removeListener(this);
    }

    @Override
    @Deprecated
    public void addHandler(ILevel universe, InputSystem inputSystem) {
        getProperty(universe).addListener(this);
    }

    @Override
    public void addHandler(ILevel universe) {
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
