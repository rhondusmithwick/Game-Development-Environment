package animation;/**
 * Created by rhondusmithwick on 4/21/16.
 *
 * @author Rhondu Smithwick
 */

import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Dimension2D dimensions = new Dimension2D(800, 600);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SpriteUtility spriteUtility = new SpriteUtility();
        spriteUtility.init(primaryStage, dimensions);
        primaryStage.show();
    }
}
