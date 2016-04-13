package animation;

import java.io.File;

import api.IEntity;
import model.component.visual.ImagePath;
import model.entity.Entity;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SandboxFX extends Application {
	//private static final File FILE = new File("resources/spriteSheets/spritesheet.png");
    //private static final Image IMAGE = new Image(FILE.toURI().toString());

    //private static final int COLUMNS  =   4;
    //private static final int COUNT    =  16;
//    private static final int OFFSET_X =  0;
//    private static final int OFFSET_Y =  0;
//    private static final int WIDTH    = 125;
//    private static final int HEIGHT   = 125;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Horse in Motion");
        //ImagePath imagePath = new ImagePath(null,125, 125, "resources/spriteSheets/spritesheet.png",new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT), true, 1000.0, 1000.0, 16, 4);
        //System.out.println(imagePath.getSpriteSheet());
        //final ImageView imageView = new ImageView(new Image(new File(imagePath.getSpriteSheet()).toURI().toString()));
        //imageView.setViewport(imagePath.getViewPort());

//        final Animation animation = new SpriteAnimation(
//                imageView,
//                Duration.millis(imagePath.getFrameDuration()),
//                imagePath.getTotalFrames(), imagePath.getColumns(),
//                OFFSET_X, OFFSET_Y,
//                imagePath.getImageWidth(), imagePath.getImageHeight()
//        );
        IEntity entity = new Entity();
        Animator animator = new Animator();
        ImagePath imagePath = new ImagePath(null, 125, 125, "resources/spriteSheets/spritesheet.png","spritesheet");
		entity.addComponent(imagePath);
        Animation animation = animator.createAnimation("walkleft",entity);
		animation.setCycleCount(Animation.INDEFINITE);
        animation.play();

        primaryStage.setScene(new Scene(new Group(animator.getImageView(entity))));
        primaryStage.show();
    }
}