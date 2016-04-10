package gui;

import java.util.ResourceBundle;

import api.ISerializable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GuiObjectDropDown extends GuiObject {	private static final double WIDTH = 150;
	private ResourceBundle cssResources = ResourceBundle.getBundle("CSSClasses");
	private ComboBox<String> dropDown;
	private VBox dropDownBox;
	private Label dropDownLabel;
	public GuiObjectDropDown(String name, String resourceBundle,EventHandler<ActionEvent> event, Property<?> property, ListProperty<?> list, ISerializable serial) {
		super(name, resourceBundle);
		dropDown = new ComboBox<String>();
		dropDown.setMaxWidth(WIDTH);
		dropDown.itemsProperty().bind((ObservableValue<? extends ObservableList<String>>) list);		
		dropDown.setValue(getResourceBundle().getString(name+"Default"));
		dropDownLabel = new Label(getResourceBundle().getString(name+"Label"));
		bindProperty(property);
		addHandler(event);
	}

	public void bindProperty(Property property) {
		dropDown.valueProperty().bindBidirectional(property);
	}

	@Override
	public Object getCurrentValue() {
		return dropDown.getValue();
	}

	public void addHandler(EventHandler<ActionEvent> event) {
		dropDown.setOnAction(event);
	}

	@Override
	public Control getControl() {
		return dropDown;
	}

	@Override
	public Object getGuiNode() {
		dropDownBox = new VBox();
		dropDownBox.getStyleClass().add(cssResources.getString("VBOX"));
		dropDownBox.getChildren().addAll(dropDownLabel,dropDown);
		return dropDownBox;
	}



}
