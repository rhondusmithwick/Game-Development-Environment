package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.enums.GUISize;

public class Main extends Application {

    /**
     * Launches our program.
     *
     * @param args
     */

    public static void main (String[] args) {
        launch(args);
    }

    /**
     * Sets up a stage to launch our window and initializes the splash screen.
     *
     * @param stage
     */

    @Override
    public void start (Stage stage) {
        System.setProperty("glass.accessible.force", "false");
        Stage myStage = stage;
        myStage.setTitle("VOOGA");
        myStage.setWidth(GUISize.MAIN_SIZE.getSize());
        myStage.setHeight(GUISize.MAIN_SIZE.getSize());
        Vooga vooga = new Vooga(myStage);
        vooga.init();
    }
}