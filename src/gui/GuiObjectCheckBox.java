package gui;

import api.ISerializable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;

public class GuiObjectCheckBox extends GuiObject{
	private CheckBox checkBox;
	
	public GuiObjectCheckBox(String name, String resourceBundle,EventHandler<ActionEvent> event, Property<?> property, ListProperty<?> list, ISerializable serial) {
		super(name, resourceBundle);
		checkBox = new CheckBox(getResourceBundle().getString(name+"Label"));
		checkBox.setSelected(Boolean.parseBoolean(getResourceBundle().getString(name + "Default")));
	}
	
	public void initialize(EventHandler<ActionEvent> event, Property<Boolean> property){
		addHandler(event);
		bindProperty(property);
	}


	private void bindProperty(Property<Boolean> property) {
		checkBox.selectedProperty().bindBidirectional(property);
	}


	private void addHandler(EventHandler<ActionEvent> event) {
		checkBox.setOnAction(event);
	}


	@Override
	public Object getCurrentValue() {
		return checkBox.isSelected();
	}


	@Override
	public Object getGuiNode() {
		return checkBox;
	}


}
