package model.vooga;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class Utilities {

    /**
     * Show an error with a certain message.
     *
     * @param error message
     */

    public static void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.show();
    }

    /**
     * Makes a button with a name and event.
     *
     * @param name  of button
     * @param event to occur when button is pressed
     * @return the button node with a name and event
     */

    public static Button makeButton(String nameProperty, EventHandler<ActionEvent> handler) {
        Button button = new Button();
        button.setText(nameProperty);
        button.setOnAction(handler);
        return button;
    }

    /**
     * Creates a combo box with the following features:
     *
     * @param prompt  (the name of the combo box)
     * @param choices in the combo box
     * @param event   to occur when the combo box is selected
     * @return the combo box with the above features.
     */

    public static ComboBox<String> makeComboBox(String prompt, ObservableList<String> choices, EventHandler<ActionEvent> event) {
        ComboBox<String> comboBox = new ComboBox<String>(choices);
        comboBox.setEditable(false);
        comboBox.setPromptText(prompt);
        comboBox.setOnAction(event);
        return comboBox;
    }


}