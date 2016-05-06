package view.componentdisplays;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Slider;
import view.enums.DefaultStrings;

/**
 * slider for integer properties
 *
 * @author calinelson
 */
public class ComponentDisplayIntegerSlider extends ComponentDisplaySlider {

    /**
     * create new integer slider instance
     *
     * @param name           name of property
     * @param resourceBundle settings resource bundle
     * @param language       display lnaguage
     * @param property       property to change
     * @param object
     */
    public ComponentDisplayIntegerSlider (String name, String resourceBundle, String language, SimpleObjectProperty<Integer> property) {
        super(name, resourceBundle, language, property);
    }

    @Override
    protected Slider createSlider (String name, SimpleObjectProperty<?> property) {
        Slider slider = new Slider(Integer.parseInt(getResourceBundle().getString(name + DefaultStrings.SLIDER_MIN.getDefault())), 
        		Integer.parseInt(getResourceBundle().getString(name + DefaultStrings.SLIDER_MAX.getDefault())), (Integer) property.getValue());
        slider.setMajorTickUnit(Integer.parseInt(getResourceBundle().getString(name + DefaultStrings.SLIDER_MAJOR_INC.getDefault())));
        slider.setMinorTickCount(Integer.parseInt(getResourceBundle().getString(name + DefaultStrings.SLIDER_MINOR_INC.getDefault())));
        slider.setSnapToTicks(true);
        slider.setValue((Integer) property.getValue());
        return slider;
    }

	@Override
	protected double getSliderValue() {
		return ((Double) super.getCurrentValue()).intValue();
	}

}
