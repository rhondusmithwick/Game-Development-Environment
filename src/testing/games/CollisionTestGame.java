package testing.games;

/**
 * Created by cyao42 on 4/22/2016.
 * <p>
 * Author: Carolyn Yao
 * refactored version of game for testing collision events.
 */

import api.IEntity;
import api.IEventSystem;
import api.ILevel;
import api.IPhysicsEngine;
import events.Action;
import events.EventFactory;
import events.Trigger;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.component.character.Health;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.physics.Collision;
import model.component.physics.Mass;
import model.component.physics.RestitutionCoefficient;
import model.component.visual.Sprite;
import model.entity.Entity;
import model.entity.Level;

import java.util.HashMap;
import java.util.Map;

public class CollisionTestGame {

    public static final String TITLE = "Collision test game";
    public static final int KEY_INPUT_SPEED = 5;
    private static Group root;
    private final ILevel universe = new Level();
    //private IEntity character;
    //private IEntity platform;
    private final String IMAGE_PATH_BLASTOISE = "resources/images/blastoise.png";
    private final String IMAGE_PATH_CHARIZARD = "resources/images/charizard.png";
    private final String healthScriptPath = "resources/groovyScripts/CollisionGameCollideHealth.groovy";
    private final String transformScriptPath = "resources/groovyScripts/CollisionGameHealthChangeImage.groovy";
    private final String moveRightScriptPath = "resources/groovyScripts/keyInputMoveRight.groovy";
    private final String moveLeftScriptPath = "resources/groovyScripts/keyInputMoveLeft.groovy";
    private final String jumpScriptPath = "resources/groovyScripts/keyInputJump.groovy";
    EventFactory eventFactory = new EventFactory();
    //private final EventSystem eventSystem = new EventSystem(universe);
    private IEventSystem eventSystem = universe.getEventSystem();
    private IPhysicsEngine physics = universe.getPhysicsEngine();
    private Scene myScene;

    /**
     * Returns name of the game.
     */
    public String getTitle () {
        return TITLE;
    }

    /**
     * Create the game's scene
     */
    public Scene init (int width, int height) {
        // Create a scene graph to organize the scene
        root = new Group();
        // Create a place to see the shapes
        myScene = new Scene(root, width, height, Color.WHITE);
        myScene.setOnKeyPressed(e -> eventSystem.takeInput(e));
        initEngine();
        return myScene;
    }

    public void initEngine () {
        Entity char1 = addCharacter("Anolyn", "blastoise.xml", IMAGE_PATH_BLASTOISE, 50.0, 200.0, "1");
        Entity char2 = addCharacter("Cani", "charizard.xml", IMAGE_PATH_CHARIZARD, 200.0, 200.0, "2");
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("entityID", char1.getID());
        registerEventSetup("PropertyTrigger", healthScriptPath, parameters, char1.getID(), char1.getComponent(Position.class).getClass(),
                char1.getComponent(Position.class).getProperty("YPosition"));
        registerEventSetup("PropertyTrigger", healthScriptPath, parameters, char1.getID(), char1.getComponent(Collision.class).getClass(),
                char1.getComponent(Collision.class).getProperty("CollidingIDs"));
        registerEventSetup("PropertyTrigger", transformScriptPath, parameters, char1.getID(), char1.getComponent(Health.class).getClass(),
                char1.getComponent(Health.class).getProperty("Health"));
        registerEventSetup("KeyTrigger", moveRightScriptPath, parameters, "D");
        registerEventSetup("KeyTrigger", moveLeftScriptPath, parameters, "A");
        registerEventSetup("KeyTrigger", jumpScriptPath, parameters, "W");
        parameters.keySet().clear();
        parameters.put("entityID", char2.getID());
        registerEventSetup("KeyTrigger", moveLeftScriptPath, parameters, "J");
        registerEventSetup("KeyTrigger", moveRightScriptPath, parameters, "L");
    }

    private Entity addCharacter (String name, String XMLName, String imagePath, Double posX, Double posY, String id) {
        //int var = 0;
        //if (var == 0) {
        Entity character = new Entity(name);
        character.forceAddComponent(new Health((double) 100), true);
        Position pos = new Position(posX, posY);
        character.forceAddComponent(pos, true);
        character.forceAddComponent(new Sprite(imagePath), true);
        character.forceAddComponent(new Velocity(0, 0), true);
        character.forceAddComponent(new Mass(100), true);
        character.addComponent(new Collision("")); // instantiated by string instead of collection
        character.forceAddComponent(new RestitutionCoefficient(0.5), true);
        universe.addEntity(character);
        character.addComponent(new Sprite(imagePath));
        drawCharacter(character);
        return character;
    }

    public void registerEventSetup (String className, String scriptName, Map<String, Object> params, Object... args) {
        //Pair<Trigger, Action> event = eventFactory.createEvent(className, scriptName, args);
        Trigger trigger = eventFactory.createTrigger(className, args);
        Action action = new Action(scriptName, params);
        eventSystem.registerEvent(trigger, action);
    }

    public void step (double dt) {
        physics.update(universe, dt);
        eventSystem.updateInputs(dt);
    }

    public void drawCharacter (IEntity character) {
        Sprite imgPath = character.getComponent(Sprite.class);
        ImageView charSprite = imgPath.getImageView();
        charSprite.setLayoutX(character.getComponent(Position.class).getX());
        charSprite.setLayoutY(character.getComponent(Position.class).getY());
        charSprite.setPreserveRatio(true);
        root.getChildren().add(charSprite);
    }

}
