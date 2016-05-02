package testing.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.entity.Level;
import view.View;
import view.enums.GUISize;

public class DemoMain extends Application {

    private Stage myStage;

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
    public void start (Stage stage) {
        myStage = stage;
        myStage.setTitle("VOOGA");
        myStage.setWidth(GUISize.MAIN_SIZE.getSize());
        myStage.setHeight(GUISize.MAIN_SIZE.getSize());

        View view = new View(2000, 2000, 2000, 2000, new Level(), "english", true);
        Pane pane = view.getPane();
        Scene scene = new Scene(pane, 500, 500);
        stage.setScene(scene);
        stage.show();
        view.setScene(scene);
    }

}
