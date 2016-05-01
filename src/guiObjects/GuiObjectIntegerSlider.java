package guiObjects;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Slider;

/**
 * slider for integer properties
 *
 * @author calinelson
 */
public class GuiObjectIntegerSlider extends GuiObjectSlider {

    /**
     * create new integer slider instance
     *
     * @param name           name of property
     * @param resourceBundle settings resource bundle
     * @param language       display lnaguage
     * @param property       property to change
     * @param object
     */
    public GuiObjectIntegerSlider (String name, String resourceBundle, String language, SimpleObjectProperty<?> property) {
        super(name, resourceBundle, language, property);
    }

    @Override
    protected Slider createSlider (String name, SimpleObjectProperty<?> property) {
        Slider slider = new Slider(Integer.parseInt(getResourceBundle().getString(name + "Min")), Integer.parseInt(getResourceBundle().getString(name + "Max")), (Integer) property.getValue());
        slider.setMajorTickUnit(Integer.parseInt(getResourceBundle().getString(name + "MajorIncrement")));
        slider.setMinorTickCount(Integer.parseInt(getResourceBundle().getString(name + "MinorIncrement")));
        slider.setSnapToTicks(true);
        slider.setValue((Integer) property.getValue());
        return slider;
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected void bindProperty (SimpleObjectProperty property, Slider slider) {
        slider.valueProperty().addListener((ov, old_val, new_val) -> {
            SimpleObjectProperty<Integer> newproperty = (SimpleObjectProperty<Integer>) property;
            newproperty.setValue(((Double) slider.valueProperty().get()).intValue());

        });
    }

}
