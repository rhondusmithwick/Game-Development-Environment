package testing;

import javafx.application.Application;
import javafx.stage.Stage;
import model.component.visual.Sprite;


/**
 * Created by rhondusmithwick on 4/13/16.
 *
 * @author Rhondu Smithwick
 */
public class ImagePathWriteTest extends Application {
    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage primaryStage) throws Exception {
        test();
    }

    public void test () {
        Sprite path = new Sprite();
        Sprite path2 = path.clone(Sprite.class);
        System.out.println(path2.getImageView());
    }
}
