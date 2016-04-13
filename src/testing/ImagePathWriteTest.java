package testing;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.component.visual.ImagePath;


/**
 * Created by rhondusmithwick on 4/13/16.
 *
 * @author Rhondu Smithwick
 */
public class ImagePathWriteTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        test();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void test() {
        ImagePath path = new ImagePath();
        ImagePath path2 = path.clone(ImagePath.class);
        System.out.println(path2.getImageView());
    }
}
