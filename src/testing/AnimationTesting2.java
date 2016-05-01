package testing;/**
 * Created by rhondusmithwick on 4/30/16.
 *
 * @author Rhondu Smithwick
 */

import api.IEntity;
import api.IEntitySystem;
import api.ILevel;
import events.Action;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.component.movement.Velocity;
import model.component.visual.AnimatedSprite;
import model.entity.Entity;
import model.entity.EntitySystem;
import model.entity.Level;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.HashMap;
import java.util.Map;

public class AnimationTesting2 extends Application {

    private static final String SCRIPT_PATH = "resources/groovyScripts/AnimationScript.groovy";
    private static final String STOP_PATH = "resources/groovyScripts/StopPerson.groovy";
    private static final String SPRITE_PATH = "resources/spriteSheets/ryuBlue.gif";
    private static final String SPRITE_PROPERTIES = "spriteProperties/aniryu";
    private transient ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage primaryStage) {
        IEntity entity = new Entity();
        entity.forceAddComponent(new AnimatedSprite(SPRITE_PATH, SPRITE_PROPERTIES), true);
        AnimatedSprite animatedSprite = entity.getComponent(AnimatedSprite.class);
        ImageView imageView = animatedSprite.getImageView();
        ILevel level = new Level();
        level.addEntity(entity);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("entityID", entity.getID());
        parameters.put("animationName", "LeftPunch");
        Action action = new Action(SCRIPT_PATH, parameters);
        Action action2 = new Action(STOP_PATH, parameters);
        primaryStage.setTitle("Animation Test");
        Group group = new Group();
        group.getChildren().add(animatedSprite.getImageView());
        Scene scene = new Scene(group);
        entity.forceAddComponent(new Velocity(50, 50), true);
        scene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.RIGHT)) {
                action.activate(engine, level);
                double oldX = imageView.getLayoutX();
                imageView.setLayoutX(oldX + 5);
            }
            if (e.getCode().equals(KeyCode.ENTER)) {
                action2.activate(engine, level);
                System.out.println(entity.getComponent(Velocity.class));
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
