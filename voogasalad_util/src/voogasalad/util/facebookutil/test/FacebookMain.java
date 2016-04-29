package voogasalad.util.facebookutil.test;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Creates and runs a simple browser based on the JavaFX library
 *
 */
public class FacebookMain extends Application {

    @Override
    public void start (Stage stage) {
        // create program specific components
        BrowserView display = new BrowserView();
        // give the window a title
        // add our user interface components to Frame and show it
        stage.setScene(display.getScene());
        stage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}
