package gui;

import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class GuiObjectSlider extends GuiObject{
	private Slider slider;
	private Label textLabel;
	private Label numLabel;


	public GuiObjectSlider(String name, String resourceBundle,EventHandler<ActionEvent> event, Property property,double min,double max, double initialValue, double increment) {
		super(name, resourceBundle);

		slider = new Slider(min,max,initialValue); 
		slider.setShowTickMarks(true);
		slider.setBlockIncrement(increment);
		textLabel = new Label(getResourceBundle().getString(getObjectName()+"LABEL"));
		numLabel = new Label(Double.toString(slider.getValue()));
		bindProperty(property);
	}



	private void bindProperty(Property property) {
		slider.valueProperty().bindBidirectional(property);
	}



	@Override
	public Object getCurrentValue() {
		return slider.getValue();
	}



	@Override
	public Control getControl() {
		return slider;
	}



	@Override
	public Object getGuiNode() {
		VBox vbox = new VBox();
		vbox.getChildren().addAll(textLabel,slider,numLabel);
		return vbox;
	}


}
