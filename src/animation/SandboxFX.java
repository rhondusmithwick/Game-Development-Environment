package animation;

import api.IEntity;
import model.component.visual.ImagePath;
import model.entity.Entity;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SandboxFX extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Horse in Motion");

        IEntity runningEntity = new Entity();
        Animator animator = new Animator();
        ImagePath runningImagePath = new ImagePath("resources/spriteSheets/spritesheet.png","spritesheet");
		runningEntity.addComponent(runningImagePath);
		IEntity megamanEntity = new Entity();
		ImagePath megaManImagePath = new ImagePath("resources/spriteSheets/sonic.png","Sonic");
		megamanEntity.addComponent(megaManImagePath);

		Animation runningAnimation = animator.createAnimation("walkleft",runningEntity);
        Animation megaManAnimation = animator.createAnimation("ball",megamanEntity);

        runningAnimation.play();
        megaManAnimation.play();
        Group group = new Group();
        group.getChildren().addAll(runningImagePath.getImageView(),megaManImagePath.getImageView());
        primaryStage.setScene(new Scene(group));
        primaryStage.show();
    }
}