package gui;


import javafx.beans.property.Property;
import javafx.scene.control.Control;
import javafx.scene.control.Label;


public class GuiObjectLabel extends GuiObject{
	private Label myLabel;
	public GuiObjectLabel(String name, String resourceBundle, Property property) {
		super(name, resourceBundle);
		myLabel = new Label(getResourceBundle().getString(getObjectName()+ "LABEL"));
		bindProperty(property);

	}


	private void bindProperty(Property property) {
		myLabel.textProperty().bind(property);
		
	}


	@Override
	public Object getCurrentValue() {
		return myLabel.getText();
	}

	@Override
	public Control getControl() {
		return myLabel;
	}

	@Override
	public Object getGuiNode() {
		return myLabel;
	}



}