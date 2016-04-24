package testing.games;

import api.IEntity;
import api.IEventSystem;
import api.ILevel;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import datamanagement.XMLReader;
import events.Action;
import events.EventSystem;
import events.KeyTrigger;
import events.PropertyTrigger;
import events.TimeTrigger;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.component.character.Health;
import model.component.character.Score;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.physics.Gravity;
import model.component.visual.ImagePath;
import model.entity.Entity;
import model.entity.Level;
import model.physics.PhysicsEngine;

import java.io.File;
import java.io.IOException;

public class ACGame {

    public static final String TITLE = "Ani's and Carolyn's game";
    public static final int KEY_INPUT_SPEED = 5;
    private static Group root;
    private final ILevel universe = new Level();
    private IEventSystem eventSystem;
    private final PhysicsEngine physics = new PhysicsEngine();
    private IEntity character;
    private final String IMAGE_PATH = "resources/images/blastoise.png";
    private final String healthScriptPath = "resources/groovyScripts/ACGameTestScript.groovy";
    private final String moveRightScriptPath = "resources/groovyScripts/keyInputMoveRight.groovy";
    private final String moveLeftScriptPath = "resources/groovyScripts/keyInputMoveLeft.groovy";
    private final String jumpScriptPath = "resources/groovyScripts/keyInputJump.groovy";
    private final String addGravityScriptPath = "resources/groovyScripts/ACAddGravity.groovy";
    private static ImageView charSpr;
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
        myScene.setOnKeyPressed(e -> universe.getEventSystem().takeInput(e));
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
            character.forceAddComponent(pos, true);
            character.forceAddComponent(new ImagePath(IMAGE_PATH), true);
            character.forceAddComponent(new Velocity(0, 0), true);
            universe.addEntity(character);
            character.addComponent(new ImagePath(IMAGE_PATH));
            //character.addComponent(new Gravity(5000));
            character.serialize("character.xml");

            eventSystem = universe.getEventSystem();
            eventSystem.registerEvent(
                    new TimeTrigger(3.0),
                    new Action(addGravityScriptPath));
            eventSystem.registerEvent(
                    new PropertyTrigger(character.getID(), Position.class, "X"),
                    new Action(healthScriptPath));
            eventSystem.registerEvent(new KeyTrigger("D"), new Action(moveRightScriptPath));
            eventSystem.registerEvent(new KeyTrigger("A"), new Action(moveLeftScriptPath));
            eventSystem.registerEvent(new KeyTrigger("W"), new Action(jumpScriptPath));
            /*eventSystem.saveEventsToFile("eventtest.xml");
            EventFileWriter w = new EventFileWriter();
            w.addEvent(KeyTrigger.class.toString().split(" ")[1], "A", moveLeftScriptPath);
            w.addEvent(KeyTrigger.class.toString().split(" ")[1], "D", moveRightScriptPath);
            w.writeEventsToFile("eventTest2.xml");*/
        } else {
            character = new XMLReader<IEntity>().readSingleFromFile("character.xml");
            universe.addEntity(character);
            eventSystem.readEventFromFile("eventtest.xml");
        }
        charSpr = drawCharacter(character);
    }

    public void step(double dt) {
        physics.update(universe, dt);
        // inputSystem.processInputs();
        eventSystem.updateInputs(dt);
        //moveEntity(character, 1);
    }

    public ImageView drawCharacter(IEntity character) {
        ImagePath imgPath = character.getComponent(ImagePath.class);
        ImageView charSprite = imgPath.getImageView();
        charSprite.setFitHeight(100);
        charSprite.setPreserveRatio(true);
        root.getChildren().add(charSprite);
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
