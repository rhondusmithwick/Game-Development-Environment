package testing;/**
 * Created by ajonnav on 4/29/16.
 *
 * @author Anirudh Jonnavithula
 */

import api.IEntity;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.component.visual.AnimatedSprite;
import model.entity.Entity;
import voogasalad.util.spriteanimation.testing.SandBox;

public class AniAnimationTesting extends Application implements SandBox {

    private static final String SPRITE_PATH = "resources/spriteSheets/ryu.gif";
    private static final String SPRITE_PROPERTIES = "spriteProperties/aniryu";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        IEntity entity = new Entity();
        entity.forceAddComponent(new AnimatedSprite(SPRITE_PATH, SPRITE_PROPERTIES), true);
        Group root = new Group();
        Scene scene = new Scene(root, 500, 500);
        scene.onKeyPressedProperty(e->animate(scene, "RightPunch"));
        primaryStage.setScene(scene);
        
        
    }
    
    public void animate(IEntity entity, String animation) {
    	AnimatedSprite animatedSprite = entity.getComponent(AnimatedSprite.class);
    	Animation animation = entity.getComponent(AnimatedSprite.class).getAnimation("RightPunch");
        animation.play();
        init(scene, animatedSprite.getImageView(), animation);
        scene.show();
    }
    
    
}
