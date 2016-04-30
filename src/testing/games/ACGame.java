package testing.games;

import api.IEntity;
import api.IEventSystem;
import api.ILevel;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import datamanagement.XMLReader;
import events.Action;
import events.KeyTrigger;
import events.MouseTrigger;
import events.PropertyTrigger;
import javafx.animation.Animation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import model.component.character.Health;
import model.component.character.Score;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.visual.AnimatedSprite;
import model.component.visual.Sprite;
import model.entity.Entity;
import model.entity.Level;
import model.physics.PhysicsEngine;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.MouseEvent;

public class ACGame {

    public static final String TITLE = "Ani's and Carolyn's game";
    public static final int KEY_INPUT_SPEED = 5;
    private static Group root;
    private final ILevel level = new Level();
    private IEventSystem eventSystem = level.getEventSystem();
    private final PhysicsEngine physics = new PhysicsEngine();
    private IEntity character;
    private final String SPRITE_PATH = "resources/spriteSheets/ryuBlue.gif";
    private final String SPRITE_PROPERTIES = "spriteProperties/aniryu";
    private final String IMAGE_PATH = "resources/spriteSheets/aniryu.gif";
    private final String healthScriptPath = "resources/groovyScripts/ACGameTestScript.groovy";
    private final String moveRightScriptPath = "resources/groovyScripts/keyInputMoveRight.groovy";
    private final String moveLeftScriptPath = "resources/groovyScripts/keyInputMoveLeft.groovy";
    private final String jumpScriptPath = "resources/groovyScripts/keyInputJump.groovy";
    private final String addGravityScriptPath = "resources/groovyScripts/ACAddGravity.groovy";
    private final String stopScriptPath = "resources/groovyScripts/stopPerson.groovy";
    private final String kickRightScriptPath = "resources/groovyScripts/RyuKickRight.groovy";
    private ImageView charSpr;
    private Scene myScene;

    /**
     * Returns name of the game.
     */
    public String getTitle() {
        return TITLE;
    }

    /**
     * Create the game's scene
     */
    public Scene init(int width, int height) {
        // Create a scene graph to organize the scene
        root = new Group();
        // Create a place to see the shapes
        myScene = new Scene(root, width, height, Color.WHITE);
        level.setOnInput(myScene);
        initEngine();
        return myScene;
    }

    public void initEngine() {
        addCharacter();
    }

    private void addCharacter() {
        int var = 0;
        if (var == 0) {
            character = new Entity("Anolyn");
            character.forceAddComponent(new Health((double) 100), true);
            character.forceAddComponent(new Score((double) 100), true);
            Position pos = new Position(100.0, 100.0);
            character.forceAddComponent(new AnimatedSprite(SPRITE_PATH, 100, 100, SPRITE_PROPERTIES, "RightDefault"), true);
            character.forceAddComponent(pos, true);
            character.forceAddComponent(new Sprite(IMAGE_PATH), true);
            character.forceAddComponent(new Velocity(0, 0), true);
            level.getEntitySystem().addEntity(character);
            //character.addComponent(new Sprite(IMAGE_PATH));
            //character.addComponent(new Gravity(5000));
//            character.serialize("character.xml");
            character.getComponent(AnimatedSprite.class).setImageHeight(100);
            character.getComponent(AnimatedSprite.class).getImageView().setX(pos.getX());
            character.getComponent(AnimatedSprite.class).getImageView().setY(pos.getY());
            root.getChildren().add(character.getComponent(AnimatedSprite.class).getImageView());
            /*Animation animation = character.getComponent(AnimatedSprite.class).createAnimation("RightDefault");
            animation.setCycleCount(1000);
            animation.play();*/
            Map<String, Object> map = new HashMap<>();
            map.put("characterName", character.getName());
            map.put("animationName", "RightPunch");
//            eventSystem.registerEvent(
//                    new TimeTrigger(3.0),
//                    new Action(addGravityScriptPath));
            eventSystem.registerEvent(
                    new PropertyTrigger(character.getID(), Position.class, "XPosition"),
                    new Action(healthScriptPath));
            eventSystem.registerEvent(new KeyTrigger(KeyCode.getKeyCode("D"), KeyEvent.KEY_PRESSED), new Action(moveRightScriptPath, map));
            eventSystem.registerEvent(new KeyTrigger(KeyCode.getKeyCode("D"), KeyEvent.KEY_RELEASED), new Action(stopScriptPath, map));
            eventSystem.registerEvent(new KeyTrigger(KeyCode.getKeyCode("A"), KeyEvent.KEY_RELEASED), new Action(stopScriptPath, map));
            eventSystem.registerEvent(new KeyTrigger(KeyCode.SPACE, KeyEvent.KEY_PRESSED), new Action(kickRightScriptPath, map));
            eventSystem.registerEvent(new KeyTrigger(KeyCode.getKeyCode("A"), KeyEvent.KEY_PRESSED), new Action(moveLeftScriptPath));
            eventSystem.registerEvent(new KeyTrigger(KeyCode.getKeyCode("W"), KeyEvent.KEY_PRESSED), new Action(jumpScriptPath));
            eventSystem.registerEvent(new MouseTrigger(MouseButton.PRIMARY, MouseEvent.MOUSE_CLICKED), new Action(moveLeftScriptPath));
        } else {
            character = new XMLReader<IEntity>().readSingleFromFile("character.xml");
            level.getEntitySystem().addEntity(character);
            eventSystem.readEventFromFile("eventtest.xml");
        }
        //charSpr = drawCharacter(character);
    }

    public void step(double dt) {
    	
    	level.getPhysicsEngine().update(level, dt);
        // inputSystem.processInputs();
        eventSystem.updateInputs(dt);
        //root.getChildren().clear();
        
    	level.getAllEntities().stream().forEach(e->drawCharacter(e));
        //moveEntity(character, 1);
    }

    public ImageView drawCharacter(IEntity character) {
    	
    	Sprite imgPath = character.getComponent(Sprite.class);
        ImageView charSprite = character.getComponent(AnimatedSprite.class).getImageView();
        charSprite.setLayoutX(character.getComponent(Position.class).getX());
        charSprite.setLayoutY(character.getComponent(Position.class).getY());
        charSprite.setPreserveRatio(true);
        charSprite.setPreserveRatio(true);
        //root.getChildren().add(charSprite);
        return charSprite;
    }

    private Action getAction(String scriptPath) {
        String script = null;
        try {
            script = Files.toString(new File(scriptPath), Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Action(script);
    }

    private void moveEntity(IEntity character, int move) {
        Position pos = character.getComponent(Position.class);
        pos.setX(pos.getX() + move);
    }
}
