package gui;
import java.util.ResourceBundle;

import api.ISerializable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class GuiObjectColorPicker extends GuiObject{
	
	private ColorPicker colorPicker;
	private Label colorPickerLabel;
	private ResourceBundle cssResources = ResourceBundle.getBundle("CSSClasses");
	
	public GuiObjectColorPicker(String name, String resourceBundle,EventHandler<ActionEvent> event, Property<?> property, ListProperty<?> list, ISerializable serial) {
		super(name, resourceBundle);
		colorPicker = new ColorPicker(Color.web(getResourceBundle().getString(name+"Default")));
        colorPickerLabel = new Label(getResourceBundle().getString(getObjectName()+"LABEL"));
        addHandler(event);
        bindProperty(property);
	}
	
	public void initialize(){
		
	}

	@Override
	public Object getCurrentValue() {
		return colorPicker.getValue();
	}

	public void addHandler(EventHandler<ActionEvent> event) {
		colorPicker.setOnAction(event);
	}
	
	public void bindProperty(Property property){
		colorPicker.valueProperty().bindBidirectional(property);
	}
	@Override
	public Control getControl() {
		return colorPicker;
	}

	@Override
	public Object getGuiNode() {
		VBox vbox = new VBox();
		vbox.getStyleClass().add(cssResources.getString("VBOX"));
		vbox.getChildren().addAll(colorPickerLabel,colorPicker);
		return vbox;
	}




}
