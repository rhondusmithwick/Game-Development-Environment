package view.componentdisplays;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import view.enums.DefaultStrings;

import java.util.ResourceBundle;

/**
 * abstract framework for gui slider objects. These objects are used to represent and change the value
 * of integer and double properties contained by the entity.
 *
 * @author calinelson
 */

public abstract class ComponentDisplaySlider extends GuiObject {
    private final Slider slider;
    private final Label textLabel;
    private final Label numLabel;

    /**
     * constructor of new gui slider instance
     *
     * @param name           name of property being set
     * @param resourceBundle slider parameter resourcebundle
     * @param language       display language
     * @param property       property to set value for
     * @param object
     */
    public ComponentDisplaySlider (String name, String resourceBundle, String language, SimpleObjectProperty<?> property) {
        super(name, resourceBundle);
        ResourceBundle myPropertiesNames = ResourceBundle.getBundle(language + DefaultStrings.PROPERTIES.getDefault());
        this.slider = createSlider(name, property);
        textLabel = new Label(myPropertiesNames.getString(getObjectName()));
        numLabel = new Label(Double.toString(slider.getValue()));
        numLabel.textProperty().bind(Bindings.convert(slider.valueProperty()));
        bindProperty(property, slider);
    }


    /**
     * create slider
     *
     * @param name     name of property
     * @param property property to bind
     * @param object
     * @return created slider
     */
    protected abstract Slider createSlider (String name, SimpleObjectProperty<?> property);


    /**
     * bind slider value to property value
     *
     * @param property property whose value to bind
     * @param slider   slider to bind property's value to
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void bindProperty (SimpleObjectProperty property, Slider slider){
    	slider.valueProperty().addListener((ov, old_val, new_val) -> {
            property.setValue(getSliderValue());

        });
    	
    }

    /**
     * gets the sliders value based on what type of slider it is
     * @param slider slider to get value from
     * @return double value of slider
     */
    protected abstract double getSliderValue();

	/**
	 * returns the current value of the slider as an object
	 * @return sliders value as object
	 */
	@Override
    public Object getCurrentValue () {
        return slider.getValue();
    }


    @Override
    public Object getGuiNode () {
        VBox vbox = new VBox();
        vbox.getChildren().addAll(textLabel, slider, numLabel);
        return vbox;
    }


}
