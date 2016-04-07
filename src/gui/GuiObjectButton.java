package gui;


import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Control;

public class GuiObjectButton extends GuiObject{
	Button button;
	boolean onAction;
	
	public GuiObjectButton(String name,String resourceBundle, EventHandler<ActionEvent> event, Property property) {
		super(name, resourceBundle);
		button = new Button(getResourceBundle().getString(getResourceBundle().getString(name+"Button")));
		addHandler(event);
		
	}


	public void addHandler(EventHandler<ActionEvent> event){
		button.setOnAction(event);
	}

	@Override
	public Control getControl() {
		return button;
	}

	@Override
	public Object getGuiNode() {
		return button;
	}

	@Override
	public Object getCurrentValue() {
		return null;
	}



}
