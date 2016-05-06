// This entire file is part of my masterpiece.
// Cali Nelson
/**
 * This class is part of my masterpiece as its design makes it very easy to add new types of sliders to the project. 
 * These sliders are used to allow the user to set the value of certain properties of an entity they are creating or editing.
 * This slider abstract class made it very simple to add sliders that set the values of different types of properties such
 * as double properties or integer properties without having to rewrite a lot of code everytime.  Also all strings
 * displayed to the user are contained in resource bundles, so that they support our multilanguages feature. I also renamed
 * this class and its subclasses so that their names were more indicative of their purposes than they were before, and I made
 * sure that all public and protected methods had javadocs written.  As you can see from the example subclasses in the masterpiece
 * included with this class, adding a new type of slider for a new object type takes only about 20 - 30 lines of code, and the
 * writing of two simple abstract methods. It also prevents a lot of repeated or similar code from being written. So if you had
 * a long or short property that you wanted to display to the user and allow them to modify, adding such as slider would be
 * very quick and easy using this abstract class and could probably be done in about 30 lines, which is fewer lines than it would
 * take if this abstract class were not present.
 */

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
     * creates a new slider for the given property
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

	/** 
	 * returns the slider and its labels inside of a vbox
	 */
    @Override
    public Object getGuiNode () {
        VBox vbox = new VBox();
        vbox.getChildren().addAll(textLabel, slider, numLabel);
        return vbox;
    }


}
