package voogasalad.util.spriteanimation.testing;

import javafx.animation.Animation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * Created by rhondusmithwick on 4/26/16.
 *
 * @author Rhondu Smithwick
 */
public interface SandBox {
    default void init(Stage stage, ImageView imageView, Animation animation) {
        stage.setTitle("Animation Test");
        Group group = new Group();
        group.getChildren().add(imageView);
        Scene scene = new Scene(group);
        scene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.RIGHT)) {
                animation.play();
                double oldX = imageView.getLayoutX();
                imageView.setLayoutX(oldX + 5);
            }
        });
        stage.setScene(scene);
    }
}
