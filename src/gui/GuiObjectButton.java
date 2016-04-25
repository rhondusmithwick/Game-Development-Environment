package gui;


import api.ISerializable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class GuiObjectButton extends GuiObject{
	Button button;
	boolean onAction;
	
	public GuiObjectButton(String name,String resourceBundle,EventHandler<ActionEvent> event, Property<?> property, ListProperty<?> list, ISerializable serial) {
		super(name, resourceBundle);
		button = new Button(getResourceBundle().getString(getResourceBundle().getString(name+"Button")));		
	}
	
	public void initilaize(EventHandler<ActionEvent> event){
		addHandler(event);
	}
	
	public void addHandler(EventHandler<ActionEvent> event){
		button.setOnAction(event);
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
