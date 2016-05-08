// This entire file is part of my masterpiece.
// Anirudh Jonnavithula
package events;

import api.IInputSystem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.input.InputEvent;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Input system implemntation.
 *
 * @author Rhondu Smithwick,Anirudh Jonnavithula, Carolyn Yao
 */
public class InputSystem implements IInputSystem {
    private final Queue<InputEvent> firstQueue = new LinkedList<>();
    private final Queue<InputEvent> secondQueue = new LinkedList<>();
    private final SimpleObjectProperty<InputEvent> currentInput = new SimpleObjectProperty<>();
    private Queue<InputEvent> fillQ = firstQueue;
    private Queue<InputEvent> processQ = secondQueue;

    @Override
    public void takeInput (InputEvent e) {
        fillQ.add(e);
    }

    @Override
    public void processInputs () {
        toggleQueues();
        while (!processQ.isEmpty()) {
            InputEvent k = processQ.poll();
            if (k != null) {
                currentInput.set(k);
            }

        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void listenToInput (ChangeListener listener) {
        currentInput.addListener(listener);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void unListenToInput (ChangeListener listener) {
        currentInput.removeListener(listener);
    }

    @Override
    public void clearInputs () {
        firstQueue.clear();
        secondQueue.clear();
    }

    private void toggleQueues () {
        if (fillQ == firstQueue) {
            fillQ = secondQueue;
            processQ = firstQueue;
        } else {
            fillQ = firstQueue;
            processQ = secondQueue;
        }
    }

    @Override
    public InputEvent getCurrentInputEvent () {
        return currentInput.get();
    }

}