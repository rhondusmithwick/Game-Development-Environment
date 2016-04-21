package events;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.input.KeyEvent;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by rhondusmithwick on 4/10/16.
 *
 * @author Rhondu Smithwick, Anirudh Jonnavithula, Carolyn Yao
 */
public class InputSystem {
    private final Queue<KeyEvent> firstQueue = new LinkedList<>();
    private final Queue<KeyEvent> secondQueue = new LinkedList<>();
    private Queue<KeyEvent> fillQ = firstQueue;
    private Queue<KeyEvent> processQ = secondQueue;

    private SimpleObjectProperty<KeyEvent> currentChar = new SimpleObjectProperty<>();

    public void takeInput(KeyEvent k) {
        fillQ.add(k);
    }

    public void processInputs() {
        toggleQueues();
        while (!processQ.isEmpty()) {
            KeyEvent k = processQ.poll();
            if (k != null) {
                currentChar.set(k);
            }
        }
    }

    public void listenToKeyPress(ChangeListener listener) {
        currentChar.addListener(listener);
    }

    public void unListenToKeyPress(ChangeListener listener) {
        currentChar.removeListener(listener);
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

    public KeyEvent getCurrentCharKeyEvent() {
        return currentChar.get();
    }

}