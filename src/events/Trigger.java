package events;

import api.ILevel;
import api.ISerializable;
import javafx.beans.value.ChangeListener;

import java.util.Observable;

/***
 * @author Anirudh Jonnavithula, Carolyn Yao
 *         Implements a ChangeListener that listens to change in a SimpleObjectProperty for now, signals EventSystem
 *         potential subclasses: PropertyTrigger, KeyTrigger, CollisionTrigger?, OtherEventTrigger
 */
public abstract class Trigger extends Observable implements ChangeListener, ISerializable {

    public abstract void clearListener(ILevel universe, InputSystem inputSystem);

    public abstract void addHandler(ILevel universe, InputSystem inputSystem);

    public String getID() {
        return toString();
    }

    @Override
    public boolean equals(Object o) {
        boolean isInstance = o instanceof Trigger;
        if (!isInstance) {
            return false;
        }
        Trigger t = (Trigger) o;
        return t.getID().equals(getID());
    }

}