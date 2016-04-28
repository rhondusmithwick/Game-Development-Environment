package voogasalad.util.spriteanimation.main;

/**
 * Runs the application.
 *
 * @author Rhondu Smithwick
 */

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SpriteUtility spriteUtility = new SpriteUtility();
        spriteUtility.init(primaryStage);
        primaryStage.show();
    }
}
