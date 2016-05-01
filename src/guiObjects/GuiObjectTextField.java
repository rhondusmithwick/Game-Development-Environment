package guiObjects;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import view.enums.DefaultStrings;

import java.util.ResourceBundle;

public class GuiObjectTextField extends GuiObject {
    private ResourceBundle myPropertiesNames;
    private TextField textField;
    private Label textFieldLabel;

    public GuiObjectTextField (String name, String resourceBundle, String language, SimpleObjectProperty<?> property) {
        super(name, resourceBundle);
        this.myPropertiesNames = ResourceBundle.getBundle(language + DefaultStrings.PROPERTIES.getDefault());
        textFieldLabel = new Label(myPropertiesNames.getString(getObjectName()));
        textField = new TextField();
        bindProperty(property, textField);
    }

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
