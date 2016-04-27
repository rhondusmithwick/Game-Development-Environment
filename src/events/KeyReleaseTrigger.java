package events;

import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyReleaseTrigger extends KeyTrigger {

	public KeyReleaseTrigger(KeyCode key) {
		super(key);
	}

	@Override
	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (((KeyEvent) newValue).getCode() == getKeyCode()) {
            setChanged();
            notifyObservers();
        }
    }
}
