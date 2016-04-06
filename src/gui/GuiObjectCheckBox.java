package gui;

import java.util.ResourceBundle;

import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;

public class GuiObjectCheckBox extends GuiObject{
	private CheckBox checkBox;
	
	public GuiObjectCheckBox(String name, String resourceBundle,boolean isChecked,EventHandler<ActionEvent> event, Property property) {
		super(name, resourceBundle);
		checkBox = new CheckBox(getResourceBundle().getString(getObjectName()+"LABEL"));
		checkBox.setSelected(isChecked);

		addHandler(event);
		bindProperty(property);

	}


	private void bindProperty(Property property) {
		checkBox.selectedProperty().bindBidirectional(property);
	}


	private void addHandler(EventHandler<ActionEvent> event) {
		checkBox.setOnAction(event);
	}

	@Override
	public Control getControl() {
		return checkBox;
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
