package voogasalad.util.facebookutil.test;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 * This class demos a working example of how the utility posts directly to Twitter
 * 
 * @author Dhrumil
 *
 */
public class TestTwitter extends Application {

    @Override
    public void start (Stage stage) {
        BrowserView display = new BrowserView();
        stage.setScene(display.getScene());
        stage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }

}
