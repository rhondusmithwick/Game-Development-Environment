package testing;/**
 * Created by rhondusmithwick on 4/27/16.
 *
 * @author Rhondu Smithwick
 */

import api.IEntity;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.stage.Stage;
import model.component.visual.AnimatedSprite;
import model.entity.Entity;
import voogasalad.util.spriteanimation.testing.SandBox;

public class AnaimationTesting extends Application implements SandBox {

    private static final String SPRITE_PATH = "resources/spriteSheets/ryuBlue.gif";
    private static final String SPRITE_PROPERTIES = "spriteProperties/aniryu";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        IEntity entity = new Entity();
        entity.forceAddComponent(new AnimatedSprite(SPRITE_PATH, SPRITE_PROPERTIES), true);
        AnimatedSprite animatedSprite = entity.getComponent(AnimatedSprite.class);
        System.out.println(animatedSprite.getAnimationNames());
        Animation animation = entity.getComponent(AnimatedSprite.class).createAnimation("LeftPunch");
        animation.play();
        init(primaryStage, animatedSprite.getImageView(), animation);
        primaryStage.show();
    }
}
