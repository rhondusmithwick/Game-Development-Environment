package events;

import api.IComponent;
import api.IEntity;
import api.ILevel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import voogasalad.util.reflection.Reflection;

/***
 * @author Anirudh Jonnavithula, Carolyn Yao Implements a ChangeListener that
 *         listens to change in a SimpleObjectProperty.
 */

public class PropertyTrigger extends Trigger {
    private final String entityID;
    private Class<? extends IComponent> componentClass;
    private final String propertyName;
    private final String characterComponentPath = "model.component.character.";
    //private Reflection reflectionUtil = new Reflection();

    public PropertyTrigger(String entityID, Class<? extends IComponent> componentClass, String propertyName) {
        this.entityID = entityID;
        this.componentClass = componentClass;
        this.propertyName = propertyName;
    }

//    public PropertyTrigger(Map<String, String> triggerMapDescription) {
//        entityID = triggerMapDescription.get("entityID");
//        String componentClassName = characterComponentPath+triggerMapDescription.get("component");
//        componentClass = (Class<? extends IComponent>) Reflection.createInstance(componentClassName, new ArrayList<String>());
////        try {
////            componentClass = (Class<? extends IComponent>) Class.forName(characterComponentPath+triggerMapDescription.get("component"));
////        } catch(ClassNotFoundException e) {
////            System.out.println("component " + triggerMapDescription.get("component"));
////            componentClass = null;
////        }
//        propertyName = triggerMapDescription.get("propertyName");
//    }
    
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

    private SimpleObjectProperty<?> getProperty(ILevel level) {
        IEntity entity = level.getEntitySystem().getEntity(entityID);
        IComponent component = entity.getComponent(componentClass);
        return component.getProperty(propertyName);
    }

    @Override
    public String toString() {
        return String.format("%s; %s; %s; %s", getClass().getSimpleName(), entityID, componentClass.getSimpleName(),
                propertyName);
    }

}
