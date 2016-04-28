package voogasalad.util.spriteanimation.utility;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Slider;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A class for GUI Utilities.
 * <p>
 * Taken from our main project (methods have been removed).
 * </p>
 *
 * @author Bruna Liborio
 */

public class GUIUtilities {


    /**
     * Show an alert with a certain message and a given type.
     *
     * @param String    title: title of alert, could be null
     * @param String    header: header for the alert, could be null
     * @param String    message: longer message further describing the alert, could be
     *                  null (though all three above shouldn't be null at the same
     *                  time or the alert is useless)
     * @param AlertType type: type of alert to display, i.e. ERROR, INFORMATION,
     *                  CONFIRMATION
     * @return boolean result.get() == ButtonType.OK: returns true is the user
     * clicked the OK button, returns false otherwise
     */

    public static boolean showAlert(String title, String header, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return (result.get() == ButtonType.OK);
    }

    /**
     * Show an error with a certain message.
     *
     * @param error message
     */

    public static void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * Makes a button with a given name and event.
     *
     * @param String                    name: name of button
     * @param EventHandler<ActionEvent> handler: action/method to occur when button is pressed
     * @return Button button: the button node tied to its name and event
     */

    public static Button makeButton(String name, EventHandler<ActionEvent> handler) {
        Button button = new Button();
        button.setText(name);
        button.setOnAction(handler);
        return button;
    }


    /**
     * Prompts a file chooser box for the user to choose a file with ONE
     * extension filter
     *
     * @param ExtensionFilter extension: file extension that the user can choose from, all
     *                        others are not allowed
     * @param String          prompt: prompt for the file chooser box
     * @return File: return file selected by the user Note: this method works
     * with promptAndGetFile(List<ExtensionFilter> filters, String
     * prompt), which allows the addition of multiple extension filters,
     * whereas this method is called with only one extension filter is
     * needed; pairing the methods reduces replicated code
     */

    public static File promptAndGetFile(ExtensionFilter extension, String prompt) {
        List<ExtensionFilter> filters = new ArrayList<>();
        filters.add(extension);
        return promptAndGetFile(filters, prompt);
    }

    /**
     * Prompts a file chooser box for the user to choose a file with MULTIPLE
     * extension filters
     *
     * @param List<ExtensionFilter> filters: file extensions that the user can choose from, all
     *                              others are not allowed
     * @param String                prompt: prompt for the file chooser box
     * @return File file: return file selected by the user
     */
    public static File promptAndGetFile(List<ExtensionFilter> filters, String prompt) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(prompt);
        fileChooser.getExtensionFilters().addAll(filters);
        return fileChooser.showOpenDialog(new Stage());
    }


    /**
     * Creates a new slider, setting all necessary intervals and locations.
     *
     * @param listener the changeListener the slider is listening to and event/method it calls when the changeImage occurs
     * @param start    the start of the slider range
     * @param end      the end of the slider range
     * @return slider                            the new slider
     */
    public static Slider makeSlider(ChangeListener<Number> listener, double start, double end, double currVal) {
        Slider slider = new Slider(start, end, currVal);
        slider.setShowTickMarks(true);
        slider.valueProperty().addListener(listener);
        return slider;
    }

    @SuppressWarnings("rawtypes")
    public static Dialog popUp(String title, String header) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        return dialog;
    }

}
