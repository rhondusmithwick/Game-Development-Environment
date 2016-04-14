package games;

import datamanagement.XMLReader;
import events.Action;
import events.EventSystem;
import events.InputSystem;
import events.KeyTrigger;
import events.PropertyTrigger;
import events.Trigger;
import games.ACGameXChangeListener;
import model.component.character.Health;
import model.component.character.Score;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.visual.ImagePath;
import model.entity.EntitySystem;
import model.physics.PhysicsEngine;
import api.IComponent;
import api.IEntity;
import api.IEntitySystem;

import java.io.File;
import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.entity.Entity;

public class ACGame {
	
    public static final String TITLE = "Ani's and Carolyn's game";
	private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
    public static final int KEY_INPUT_SPEED = 5;
    private static final double GROWTH_RATE = 1.1;
    private static final int BOUNCER_SPEED = 30;
    private static Group root;
	private final IEntitySystem universe = new EntitySystem();
	private final InputSystem inputSystem = new InputSystem(universe);
	private final EventSystem eventSystem = new EventSystem(universe, inputSystem);
	private final PhysicsEngine physics = new PhysicsEngine();
	private IEntity character;
	private final String IMAGE_PATH = "resources/images/blastoise.png";
	private final String healthScriptPath = "resources/groovyScripts/ACGameTestScript.groovy";
	private final String moveRightScriptPath = "resources/groovyScripts/keyInputMoveRight.groovy";
	private final String moveLeftScriptPath = "resources/groovyScripts/keyInputMoveLeft.groovy";
	private static ImageView charSpr; 
    
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
        myScene.setOnKeyPressed(e -> inputSystem.take(e));
        initEngine();
        return myScene;
    }
    
    public void initEngine() { 
    	addCharacter();
    }

	private void addCharacter() {
		int var = 1;
		if(var==0) {
			character = new Entity("Anolyn");
			character.forceAddComponent(new Health((double) 100), true);
			character.forceAddComponent(new Score((double) 100), true);
			Position pos = new Position(100.0, 100.0);
			character.forceAddComponent(pos, true);
			character.forceAddComponent(new ImagePath(IMAGE_PATH), true);
			universe.addEntity(character);
	    	character.addComponent(new ImagePath(IMAGE_PATH));
			character.serialize("character.xml");
	    	eventSystem.registerEvent(new PropertyTrigger(character.getID(), character.getComponent(Position.class), 0, universe, inputSystem), new Action(healthScriptPath));
			eventSystem.registerEvent(new KeyTrigger("D", universe, inputSystem), new Action(moveRightScriptPath));
			eventSystem.registerEvent(new KeyTrigger("A", universe, inputSystem), new Action(moveLeftScriptPath));
	    	// input system add a keycode to a script 
	    	//eventSystem.registerEvent(new Trigger(inputSystem.getCurrentChar()), action);
	    	eventSystem.saveEventsToFile("eventtest.xml");
		}
		else {
			character = new XMLReader<IEntity>().readSingleFromFile("character.xml");
			universe.addEntity(character);
			eventSystem.readEventsFromFile("eventtest.xml");
		}
		charSpr = drawCharacter(character);
	}
	
	public void step(double dt) {
		physics.update(universe, dt);
		inputSystem.processInputs();
		//moveEntity(character, 1);
	}
	
	public ImageView drawCharacter(IEntity character) { 
		ImagePath imgPath = character.getComponent(ImagePath.class);
		ImageView charSprite = imgPath.getImageView();
		charSprite.setFitHeight(100);
		charSprite.setPreserveRatio(true);
		charSprite.xProperty().bind(character.getComponent(Position.class).xProperty());
		charSprite.yProperty().bind(character.getComponent(Position.class).yProperty());
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
