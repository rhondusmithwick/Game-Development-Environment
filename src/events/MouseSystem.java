package events;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.input.MouseEvent;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Roxanne Baker
 */
@Deprecated
public class MouseSystem {
    private final Queue<MouseEvent> firstQueue = new LinkedList<>();
    private final Queue<MouseEvent> secondQueue = new LinkedList<>();
    private final SimpleObjectProperty<MouseEvent> currentChar = new SimpleObjectProperty<>();
    private Queue<MouseEvent> fillQ = firstQueue;
    private Queue<MouseEvent> processQ = secondQueue;

    public void takeInput (MouseEvent k) {
        fillQ.add(k);
    }

    public void processInputs () {
        toggleQueues();
        while (!processQ.isEmpty()) {
            MouseEvent k = processQ.poll();
            if (k != null) {
                currentChar.set(k);
            }

        }
    }

    @SuppressWarnings("unchecked")
    public void listenToMousePress (ChangeListener listener) {
        currentChar.addListener(listener);
    }

    @SuppressWarnings("unchecked")
    public void unListenToMousePress (ChangeListener listener) {
        currentChar.removeListener(listener);
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

    public MouseEvent getCurrentCharKeyEvent () {
        return currentChar.get();
    }

}