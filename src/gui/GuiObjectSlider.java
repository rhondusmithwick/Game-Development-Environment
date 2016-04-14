package gui;

import javafx.beans.binding.Bindings;
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


	public GuiObjectSlider(String name, String resourceBundle,EventHandler<ActionEvent> event, Property<?> property, Object object) {
		super(name, resourceBundle);
		slider = new Slider(Integer.parseInt(getResourceBundle().getString(name+"Min")),Integer.parseInt(getResourceBundle().getString(name+ "Max")), (double) object); 
		slider.setShowTickMarks(true);
		slider.setBlockIncrement(Integer.parseInt(getResourceBundle().getString(name+"Increment")));
		textLabel = new Label(getResourceBundle().getString(getObjectName()+"Label"));
		numLabel = new Label(Double.toString(slider.getValue()));
		numLabel.textProperty().bind(Bindings.convert(slider.valueProperty()));
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
