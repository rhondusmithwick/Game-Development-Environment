package guiObjects;


import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import view.enums.DefaultStrings;

import java.util.ResourceBundle;

/**
 * @author calinelson
 */

public class GuiObjectLabel extends GuiObject {
    private final Label myLabel;

    public GuiObjectLabel (String name, String resourceBundle, String language, SimpleObjectProperty<?> property) {
        super(name, resourceBundle);
        ResourceBundle myPropertiesNames = ResourceBundle.getBundle(language + DefaultStrings.PROPERTIES.getDefault());
        ResourceBundle myResources = ResourceBundle.getBundle(language);
        myLabel = new Label(myPropertiesNames.getString(name) + myResources.getString("true"));

    }


    @Override
    public Object getCurrentValue () {
        return myLabel.getText();
    }


    @Override
    public Object getGuiNode () {
        return myLabel;
    }


}