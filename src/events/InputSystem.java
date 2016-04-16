package events;

import api.IEntitySystem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.input.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by rhondusmithwick on 4/10/16.
 *
 * @author Rhondu Smithwick, Anirudh Jonnavithula, Carolyn Yao
 */
public class InputSystem {
    private Queue<KeyEvent> q1;
    private Queue<KeyEvent> q2;
    private Queue<KeyEvent> fillQ;
    private Queue<KeyEvent> processQ;
    
    
    private SimpleObjectProperty<KeyEvent> currentChar = new SimpleObjectProperty<>(); 

    public InputSystem(IEntitySystem universe) {
    	q1 = new PriorityQueue<>();
    	q2 = new PriorityQueue<>();
    	fillQ = q1; 
    	processQ = q2;
    }

    public void take(KeyEvent k) {
		fillQ.add(k);
    }
    
    public void processInputs() { 
    	toggleQueues();
    	while(!processQ.isEmpty()) { 
    		KeyEvent k = processQ.poll();
    		if(k!=null) {
    			currentChar.set(k);
    		}
    		
    	}
    }
    
    public void listenToKeyPress(ChangeListener listener) {
    	currentChar.addListener(listener);
    }
    
    private void toggleQueues() {
    	if (fillQ == q1) { 
    		fillQ = q2; 
    		processQ = q1;
    	} else { 
    		fillQ = q1;
    		processQ = q2;
    	}
    }
    
    public KeyEvent getCurrentCharKeyEvent() { 
    	return currentChar.get();
    }
}