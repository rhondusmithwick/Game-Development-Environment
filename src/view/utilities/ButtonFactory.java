package view.utilities;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ButtonFactory {
    private ButtonFactory () {

    }

    /**
     * Makes a button with a given name and event.
     *
     * @param String                    name: name of button
     * @param EventHandler<ActionEvent> handler: action/method to occur when button is pressed
     * @return Button button: the button node tied to its name and event
     */

    public static Button makeButton (String name, EventHandler<ActionEvent> handler) {
        Button button = new Button();
        button.setText(name);
        button.setOnAction(handler);
        return button;
    }

}
