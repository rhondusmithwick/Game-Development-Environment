package events;

import javafx.beans.value.ObservableValue;
import javafx.event.EventType;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/***
 * @author ani, roxanne
 *         Authors: Anirudh Jonnavithula, Roxanne Baker
 *         Listeners that notify the event system if a specific key is pressed.
 *         Created 04/27/16
 */

public class MouseTrigger extends InputTrigger {

    private MouseButton button;

    public MouseTrigger (MouseButton button, EventType eventType) {
        super(eventType);
        setButton(button);
    }

    @Override
    public boolean meetsCriteria (ObservableValue observable, Object oldValue, Object newValue) {
        if (getEventType() == ((InputEvent) newValue).getEventType()) {
            MouseEvent mouse = (MouseEvent) newValue;
            if (mouse.getButton() == getButton()) {
                return true;
            }
        }
        return false;
    }

    public String toString () {
        return getButton().toString() + ":" + getEventType().toString();
    }

    protected MouseButton getButton () {
        return button;
    }

    private void setButton (MouseButton button) {
        this.button = button;
    }

}
