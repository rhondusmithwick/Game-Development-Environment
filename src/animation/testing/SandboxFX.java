package animation.testing;


import javafx.animation.Animation;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import static animation.animation.AnimationFactory.createAnimationFromBundle;

public class SandboxFX extends Application {

    private static final String SPRITE_PATH = "animation/testing/sonic.png";
    private static final String RESOURCE_BUNDLE = "animation/testing/Sonic";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Animation Test");
        ImageView imageView = new ImageView(SPRITE_PATH);
        Animation megaManAnimation = createAnimationFromBundle(imageView, RESOURCE_BUNDLE, "ball");
        megaManAnimation.play();
        Group group = new Group();
        group.getChildren().add(imageView);
        Scene scene = new Scene(group);
        scene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.RIGHT)) {
                megaManAnimation.play();
                double oldX = imageView.getLayoutX();
                imageView.setLayoutX(oldX + 5);
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
