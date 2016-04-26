package animation.main;


import animation.animation.Animator;
import api.IEntity;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.component.visual.ImagePath;
import model.entity.Entity;


public class SandboxFX extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Horse in Motion");

        IEntity runningEntity = new Entity();
        Animator animator = new Animator();
        ImagePath runningImagePath = new ImagePath("karatekid", "resources/spriteSheets/karatekid.gif", 0, 0, null, false, 0, 0, 0);
        runningEntity.addComponent(runningImagePath);
        //Animation runningAnimation = animator.createAnimation("hit", runningEntity);
        //runningAnimation.play();

        IEntity megamanEntity = new Entity();
        ImagePath megaManImagePath = new ImagePath("Sonic", "resources/spriteSheets/sonic.png", 0, 0, null, false, 0, 0, 0);
        megamanEntity.addComponent(megaManImagePath);

        Animation megaManAnimation = animator.createAnimation("ball", megamanEntity);
        ImageView sonicimage = megaManImagePath.getImageView();


        megaManAnimation.play();
        Group group = new Group();
        group.getChildren().add(sonicimage);
        Scene scene = new Scene(group);
        scene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.RIGHT)) {
                double oldX = sonicimage.getLayoutX();
                sonicimage.setLayoutX(oldX + 5);
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
