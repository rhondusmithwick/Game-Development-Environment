package view.componentdisplays;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Slider;
import view.enums.DefaultStrings;

/**
 * Class to set double property with slider
 *
 * @author calinelson
 */
public class GuiObjectDoubleSlider extends ComponentDisplaySlider {

    /**
     * constructor for double slider
     *
     * @param name           name of property
     * @param resourceBundle resourcebundle
     * @param language       display language
     * @param property       property to bind
     * @param object         object
     */
    public GuiObjectDoubleSlider (String name, String resourceBundle, String language, SimpleObjectProperty<Double> property) {
        super(name, resourceBundle, language, property);
    }

    @Override
    protected Slider createSlider (String name, SimpleObjectProperty<?> property) {
        Slider slider = new Slider(Double.parseDouble(getResourceBundle().getString(name + DefaultStrings.SLIDER_MIN.getDefault())),
        		Double.parseDouble(getResourceBundle().getString(name + DefaultStrings.SLIDER_MAX.getDefault())), (Double) property.getValue());
        slider.setValue((Double) property.getValue());
        return slider;
    }

	@Override
	protected double getSliderValue() {
		return (Double) super.getCurrentValue();
	}

}
