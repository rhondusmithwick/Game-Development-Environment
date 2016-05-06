// This entire file is part of my masterpiece.
// Cali Nelson

/**
 * This class is an example of a subclass of the componentDisplaySlider. As you can see it only took about 20 lines 
 * of code to add this type of slider in because the abstract class handles most of work, and the only thing needed
 * from this class is creating the slider and getting its value so that both work with an integer property. Another good 
 * design feature is that the values for setting the sliders minimum and maximum values, and the major and minor
 * tick intervals are contained in properties files
 * so that they can easily be customised for each property. So for example if you wanted the minimum for the velocity property
 * to be -100.0 but the minimum for the mass property to be 1, all you would have to do is modify the values in the 
 * guiObject/guiComponents.properties files. Also if you wanted to allow the user only to set the value of the lives property
 * in multiples of ten, but allow them to set another property in increments of 1, all you would have to do is set different 
 * minor increment values in the properties files. Thus these customization options are not hardcoded in, so each property can
 * be customized in the way that makes the most sense for that property and for the user.
 */

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
