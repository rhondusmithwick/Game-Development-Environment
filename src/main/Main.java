
package main;

import javafx.application.Application;
import javafx.stage.Stage;
import utility.FilePathRelativizer;
import view.enums.GUISize;

public class Main extends Application {

    private static final String RELATIVIZER_TESTER = "/Users/rhondusmithwick/Documents/Classes/CS308/voogasalad/resources/spriteSheets";
    private Stage myStage;

    /**
     * Sets up a stage to launch our window and initializes the splash screen.
     *
     * @param stage
     */

    @Override
    public void start (Stage stage) {
        String relative = FilePathRelativizer.relativize(RELATIVIZER_TESTER);
        System.out.println(relative);
        myStage = stage;
        myStage.setTitle("VOOGA");
        myStage.setWidth(GUISize.MAIN_SIZE.getSize());
        myStage.setHeight(GUISize.MAIN_SIZE.getSize());
        Vooga vooga = new Vooga(myStage);
        vooga.init();
    }

    /**
     * Launches our program.
     *
     * @param args
     */

    public static void main (String[] args) {
        launch(args);
    }

}