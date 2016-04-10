package voogasalad;

import static org.junit.Assert.*;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import org.junit.Test;

import model.component.movement.Velocity;
import model.entity.Entity;
import model.events.EventSystem;
import model.events.MoveEntityLeft;
import view.Editor;
import view.EditorFactory;
import api.IComponent;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;

public class EventHandlingTester {
	
	@Test
	public void addToEventSystem() {
		EventSystem eventbus = new EventSystem();
		Entity entity = new Entity(0);
		List<IComponent> components = new ArrayList<IComponent>();
		Object myObject = new Object();
		
		// it appears that if the sources are effectively equal, 
		// then the event handler should also be equal
		// will need to find a way to test for key events/mouse events
		EventObject event = new EventObject(new Double(50));
//		eventbus.registerTrigger(event, new MoveEntityLeft(entity));
//		eventbus.handleEvent(new EventObject(new Double(50)));
		Robot robot = new Robot();
		Group myGroup = new Group();
		Node myNode = new Node(myGroup);
		
		ActionEvent action = new ActionEvent(null, null, null);
		InputEvent input = new InputEvent(EventType<KeyEvent> ke);
	}
}
