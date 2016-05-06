// This entire file is part of my masterpiece.
// Cali Nelson

/**
 * This class is an example of a subclass of the componentDisplaySlider. As you can see it only took about 15 lines 
 * of code to add this type of slider in because the abstract class handles most of work, and the only thing needed
 * from this class is creating the slider and getting its value so that both work with a double property. Another good 
 * design feature is that the values for setting the sliders minimum and maximum values are contained in properties files
 * so that they can easily be customised for each property. So for example if you wanted the minimum for the velocity property
 * to be -100.0 but the minimum for the mass property to be 1, all you would have to do is modify the values in the 
 * guiObject/guiComponents.properties files. Thus these maxes and mins are not hardcoded in, so there are avenues to 
 * customize each slider to the property it represents.
 */

package view.componentdisplays;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Slider;
import view.enums.DefaultStrings;

/**
 * Class to set double property with slider
 *
 * @author calinelson
 */
public class ComponentDisplayDoubleSlider extends ComponentDisplaySlider {

    /**
     * constructor for double slider
     *
     * @param name           name of property
     * @param resourceBundle resourcebundle
     * @param language       display language
     * @param property       property to bind
     * @param object         object
     */
    public ComponentDisplayDoubleSlider (String name, String resourceBundle, String language, SimpleObjectProperty<Double> property) {
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
