package api;

import javafx.beans.value.ChangeListener;
import javafx.scene.input.InputEvent;

public interface IInputSystem {

    void takeInput (InputEvent e);

    void processInputs ();

    void listenToInput (ChangeListener listener);

    void unListenToInput (ChangeListener listener);

    void clearInputs ();

    InputEvent getCurrentInputEvent ();

}
