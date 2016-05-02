package guiObjects;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import view.enums.DefaultStrings;

import java.util.ResourceBundle;

public class GuiObjectTextField extends GuiObject {
    private final TextField textField;
    private final Label textFieldLabel;

    public GuiObjectTextField (String name, String resourceBundle, String language, SimpleObjectProperty<?> property) {
        super(name, resourceBundle);

        ResourceBundle myPropertiesNames = ResourceBundle.getBundle(language + DefaultStrings.PROPERTIES.getDefault());
        textFieldLabel = new Label(myPropertiesNames.getString(getObjectName()));
        textField = new TextField();
        bindProperty(property, textField);
    }

    @SuppressWarnings("unchecked")
    private void bindProperty (SimpleObjectProperty<?> property, TextField textField2) {
        textField.textProperty().bind((ObservableValue<? extends String>) property);
    }

    @Override
    public Object getCurrentValue () {
        return textField.getText();
    }

    @Override
    public Object getGuiNode () {
        VBox vbox = new VBox();
        vbox.getChildren().addAll(textFieldLabel, textField);
        return vbox;
    }

}
