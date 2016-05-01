package guiObjects;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import view.enums.DefaultStrings;

import java.util.ResourceBundle;

/**
 * abstract framework for gui slider objects
 *
 * @author calinelson
 */

public abstract class GuiObjectSlider extends GuiObject {
    private Slider slider;
    private Label textLabel;
    private Label numLabel;
    private ResourceBundle myPropertiesNames;

    /**
     * constructor of new gui slider instance
     *
     * @param name           name of property being set
     * @param resourceBundle slider parameter resourcebundle
     * @param language       display language
     * @param property       property to set value for
     * @param object
     */
    public GuiObjectSlider (String name, String resourceBundle, String language, SimpleObjectProperty<?> property) {
        super(name, resourceBundle);
        this.myPropertiesNames = ResourceBundle.getBundle(language + DefaultStrings.PROPERTIES.getDefault());
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
    @SuppressWarnings("rawtypes")
    protected abstract void bindProperty (SimpleObjectProperty property, Slider slider);


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
