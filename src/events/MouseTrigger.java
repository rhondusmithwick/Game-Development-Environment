package events;

import api.ILevel;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseEvent;

@Deprecated
public class MouseTrigger extends Trigger {
    private final String entityID;

    public MouseTrigger(String entityID) {
        this.entityID = entityID;
    }
    
    @Override
    public void changed(ObservableValue arg0, Object oldValue, Object newValue) {
    	// TODO:  add recognition of what thing is being clicked
        if (((MouseEvent) newValue).getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
        	System.out.println("WOLOLOLOLOLOLOLOLOOOOOOOOOOOOOOOOOOO");
        	setChanged();
            notifyObservers();        	
        }
    }

    @Override
    @Deprecated
    public void clearListener(ILevel universe, InputSystem inputSystem) {
       // getProperty(universe).removeListener(this);
    }
    
    @Override
    public void clearListener(ILevel universe) {
//        universe.getEventSystem().unListenToMousePress(this);
    }

    @Override
    @Deprecated
    public void addHandler(ILevel universe, InputSystem inputSystem) {
        //getProperty(universe).addListener(this);
    }

    @Override
    public void addHandler(ILevel universe) {
//        universe.getEventSystem().listenToMousePress(this);
    }

//    private SimpleObjectProperty<?> getProperty(ILevel universe) {
//        IEntity entity = universe.getEntity(entityID);
//        IComponent component = entity.getComponent(componentClass);
//        return component.getProperty(propertyName);
//    }

    @Override
    public String toString() {
        return String.format("%s; %s; %s; %s", getClass().getSimpleName(), entityID);
    }

}
