package events;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;

import java.util.LinkedList;
import java.util.Queue;

import api.IInputSystem;

/**
 * Created by rhondusmithwick on 4/10/16.
 *
 * @author Rhondu Smithwick, Anirudh Jonnavithula, Carolyn Yao
 */
public class InputSystem implements IInputSystem{
    private final Queue<InputEvent> firstQueue = new LinkedList<>();
    private final Queue<InputEvent> secondQueue = new LinkedList<>();
    private Queue<InputEvent> fillQ = firstQueue;
    private Queue<InputEvent> processQ = secondQueue;

    private SimpleObjectProperty<InputEvent> currentInput = new SimpleObjectProperty<>();

    @Override
    public void takeInput(InputEvent e) {
		fillQ.add(e);
    }

    @Override
    public void processInputs() {
        toggleQueues();
        while (!processQ.isEmpty()) {
            InputEvent k = processQ.poll();
            if (k != null) {
                currentInput.set(k);
            }
            
        }
    }

    @Override
    public void listenToInput(ChangeListener listener) {
        currentInput.addListener(listener);
    }

    @Override
    public void unListenToInput(ChangeListener listener) {
        currentInput.removeListener(listener);
    }
    
    @Override
    public void clearInputs() {
    	firstQueue.clear();
    	secondQueue.clear();
    }

    private void toggleQueues() {
        if (fillQ == firstQueue) {
            fillQ = secondQueue;
            processQ = firstQueue;
        } else {
            fillQ = firstQueue;
            processQ = secondQueue;
        }
    }

    @Override
    public InputEvent getCurrentInputEvent() {
        return currentInput.get();
    }

}