package guiObjects;

import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import view.enums.DefaultStrings;

/**
 * 
 * @author calinelson
 *
 */

public abstract class GuiObjectSlider extends GuiObject{
	private Slider slider;
	private Label textLabel;
	private Label numLabel;
	private ResourceBundle myPropertiesNames;


	public GuiObjectSlider(String name, String resourceBundle,String language, SimpleObjectProperty<?> property, Object object) {
		super(name, resourceBundle);
		this.myPropertiesNames= ResourceBundle.getBundle(language + DefaultStrings.PROPERTIES.getDefault());
		this.slider = createSlider(name, property, object);
		textLabel = new Label(myPropertiesNames.getString(getObjectName()));
		numLabel = new Label(Double.toString(slider.getValue()));
		numLabel.textProperty().bind(Bindings.convert(slider.valueProperty()));
		bindProperty(property, slider);
	}



	protected abstract Slider createSlider(String name, SimpleObjectProperty<?> property, Object object);



	@SuppressWarnings("rawtypes")
	protected abstract void bindProperty(SimpleObjectProperty property, Slider slider);



	@Override
	public Object getCurrentValue() {
		return slider.getValue();
	}



	@Override
	public Object getGuiNode() {
		VBox vbox = new VBox();
		vbox.getChildren().addAll(textLabel,slider,numLabel);
		return vbox;
	}


}
