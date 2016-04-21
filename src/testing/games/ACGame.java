package testing.games;

import api.IEntity;
import api.ILevel;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import datamanagement.XMLReader;
import events.Action;
import events.EventSystem;
import events.KeyTrigger;
import events.PropertyTrigger;
<<<<<<< HEAD
<<<<<<< HEAD:src/games/ACGame.java
=======
import events.Trigger;
>>>>>>> e897cf2dca9927012f184763f9a491a2e3a42bca:src/testing/games/ACGame.java
=======
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
>>>>>>> 4b37dbfbef7ed6e05fe66b5a93ac860c3b44c630
import model.component.character.Health;
import model.component.character.Score;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.physics.Gravity;
import model.component.visual.ImagePath;
import model.entity.Entity;
import model.entity.Level;
import model.physics.PhysicsEngine;
<<<<<<< HEAD
import api.IEntity;
import api.IEntitySystem;
<<<<<<< HEAD:src/games/ACGame.java
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
=======
=======
>>>>>>> 4b37dbfbef7ed6e05fe66b5a93ac860c3b44c630

import java.io.File;
import java.io.IOException;

<<<<<<< HEAD
import testing.games.ACGameXChangeListener;
>>>>>>> e897cf2dca9927012f184763f9a491a2e3a42bca:src/testing/games/ACGame.java

import javafx.scene.Group;
import javafx.scene.Scene;
<<<<<<< HEAD:src/games/ACGame.java
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
=======
>>>>>>> e897cf2dca9927012f184763f9a491a2e3a42bca:src/testing/games/ACGame.java
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.entity.Entity;

public class ACGame {
	
<<<<<<< HEAD:src/games/ACGame.java
    public final String TITLE = "Ani's and Carolyn's game";
	private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
    public final int KEY_INPUT_SPEED = 5;
	//private final Image BACKGROUND_IMAGE = new Image(getClass().getClassLoader().getResourceAsStream("src/resources/images/grassplatform.jpg"));
=======
=======
public class ACGame {

>>>>>>> 4b37dbfbef7ed6e05fe66b5a93ac860c3b44c630
    public static final String TITLE = "Ani's and Carolyn's game";
    public static final int KEY_INPUT_SPEED = 5;
>>>>>>> e897cf2dca9927012f184763f9a491a2e3a42bca:src/testing/games/ACGame.java
    private static Group root;
<<<<<<< HEAD
	private final IEntitySystem universe = new EntitySystem();
	private final InputSystem inputSystem = new InputSystem(universe);
	private final EventSystem eventSystem = new EventSystem(universe, inputSystem);
	private final PhysicsEngine physics = new PhysicsEngine();
<<<<<<< HEAD
	private final String BG_IMAGE_PATH = "resources/images/movingwaterfall.gif";
=======
	private IEntity character;
<<<<<<< HEAD:src/games/ACGame.java
>>>>>>> 0b79d97d435fa38fbacc3be9cb50331a6d43819d
=======
	private IEntity platform;
>>>>>>> e897cf2dca9927012f184763f9a491a2e3a42bca:src/testing/games/ACGame.java
	private final String IMAGE_PATH = "resources/images/blastoise.png";
	private final String IMAGE_PATH2 = "resources/images/charizard.png";
	private final String healthScriptPath = "resources/groovyScripts/ACGameTestScript.groovy";
	private final String moveRightScriptPath = "resources/groovyScripts/keyInputMoveRight.groovy";
	private final String moveLeftScriptPath = "resources/groovyScripts/keyInputMoveLeft.groovy";
<<<<<<< HEAD:src/games/ACGame.java
	private final String moveRightScriptPath2 = "resources/groovyScripts/keyInputMoveRight2.groovy";
	private final String moveLeftScriptPath2 = "resources/groovyScripts/keyInputMoveLeft2.groovy";
=======
	private final String jumpScriptPath = "resources/groovyScripts/keyInputJump.groovy";
>>>>>>> e897cf2dca9927012f184763f9a491a2e3a42bca:src/testing/games/ACGame.java
	private static ImageView charSpr; 
    
=======
    private final ILevel universe = new Level();
    private final EventSystem eventSystem = new EventSystem(universe);
    private final PhysicsEngine physics = new PhysicsEngine();
    private IEntity character;
    private IEntity platform;
    private final String IMAGE_PATH = "resources/images/blastoise.png";
    private final String healthScriptPath = "resources/groovyScripts/ACGameTestScript.groovy";
    private final String moveRightScriptPath = "resources/groovyScripts/keyInputMoveRight.groovy";
    private final String moveLeftScriptPath = "resources/groovyScripts/keyInputMoveLeft.groovy";
    private final String jumpScriptPath = "resources/groovyScripts/keyInputJump.groovy";
    private static ImageView charSpr;
>>>>>>> 4b37dbfbef7ed6e05fe66b5a93ac860c3b44c630
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
<<<<<<< HEAD
        myScene.setOnKeyPressed(e -> inputSystem.take(e));
//        Canvas canvas = new Canvas(width, width); 
//        root.getChildren().add(canvas);
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//        gc.drawImage(BACKGROUND_IMAGE, 0, 0);
        initEngine();
        return myScene;
    }
    
    public void initEngine() { 
    	addBackground();
    	addCharacter1();
    	addCharacter2();
    }

<<<<<<< HEAD:src/games/ACGame.java
    private void addBackground() { 
    	IEntity background = new Entity("Background");
    	background.forceAddComponent(new ImagePath(BG_IMAGE_PATH), true);
		Position pos = new Position(0.0, 0.0);
    	background.forceAddComponent(pos, true);
    	universe.addEntity(background);
    	//universe.
    }
	private void addCharacter1() {
		IEntity character;
=======
	private void addCharacter() {
>>>>>>> e897cf2dca9927012f184763f9a491a2e3a42bca:src/testing/games/ACGame.java
		int var = 0;
		if(var==0) {
			character = new Entity("Anolyn");
			character.forceAddComponent(new Health((double) 100), true);
			character.forceAddComponent(new Score((double) 100), true);
			Position pos = new Position(100.0, 300);
			character.forceAddComponent(pos, true);
			character.forceAddComponent(new ImagePath(IMAGE_PATH), true);
			character.forceAddComponent(new Velocity(0,0), true);
			universe.addEntity(character);
<<<<<<< HEAD:src/games/ACGame.java
	    	//character.addComponent(new ImagePath(IMAGE_PATH));
=======
	    	character.addComponent(new ImagePath(IMAGE_PATH));
	    	character.addComponent(new Gravity(5000));
>>>>>>> e897cf2dca9927012f184763f9a491a2e3a42bca:src/testing/games/ACGame.java
			character.serialize("character.xml");
			platform = new Entity("platform");
			
	    	eventSystem.registerEvent(new PropertyTrigger(character.getID(), character.getComponent(Position.class), 0, universe, inputSystem), new Action(healthScriptPath));
			eventSystem.registerEvent(new KeyTrigger("D", universe, inputSystem), new Action(moveRightScriptPath));
			eventSystem.registerEvent(new KeyTrigger("A", universe, inputSystem), new Action(moveLeftScriptPath));
<<<<<<< HEAD:src/games/ACGame.java
=======
			eventSystem.registerEvent(new KeyTrigger("W", universe, inputSystem), new Action(jumpScriptPath));
>>>>>>> e897cf2dca9927012f184763f9a491a2e3a42bca:src/testing/games/ACGame.java
	    	eventSystem.saveEventsToFile("eventtest.xml");
	    	EventFileWriter w = new EventFileWriter();
	    	w.addEvent(KeyTrigger.class.toString().split(" ")[1],"A",moveLeftScriptPath);
	    	w.addEvent(KeyTrigger.class.toString().split(" ")[1],"D",moveRightScriptPath);
	    	w.writeEventsToFile("eventTest2.xml");
		}
		else {
			character = new XMLReader<IEntity>().readSingleFromFile("character.xml");
			universe.addEntity(character);
			eventSystem.readEventsFromFilePath("eventtest.xml");
		}
		charSpr = drawCharacter(character);
	}
	
	private void addCharacter2() {
		IEntity character;
		int var = 0;
		if(var==0) {
			character = new Entity("Cani");
			character.forceAddComponent(new Health((double) 100), true);
			character.forceAddComponent(new Score((double) 100), true);
			Position pos = new Position(200.0, 300);
			//character.forceAddComponent(new Velocity(10, 10), true);
			character.forceAddComponent(pos, true);
			character.forceAddComponent(new ImagePath(IMAGE_PATH2), true);
			universe.addEntity(character);
	    	//character.addComponent(new ImagePath(IMAGE_PATH2));
			character.serialize("character1.xml");
	    	eventSystem.registerEvent(new PropertyTrigger(character.getID(), character.getComponent(Position.class), 0, universe, inputSystem), new Action(healthScriptPath));
			eventSystem.registerEvent(new KeyTrigger("P", universe, inputSystem), new Action(moveRightScriptPath2));
			eventSystem.registerEvent(new KeyTrigger("I", universe, inputSystem), new Action(moveLeftScriptPath2));
	    	eventSystem.saveEventsToFile("eventtest.xml");
		}
		else {
			character = new XMLReader<IEntity>().readSingleFromFile("character1.xml");
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
		root.getChildren().add(charSprite);
		return charSprite;
	}
	
	private void moveEntity(IEntity character, int move) { 
		 Position pos = character.getComponent(Position.class);
		 pos.setX(pos.getX() + move);
	}
=======
        myScene.setOnKeyPressed(e -> eventSystem.takeInput(e));
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
            character.addComponent(new Gravity(5000));
            character.serialize("character.xml");
            platform = new Entity("platform");

            eventSystem.registerEvent(
                    new PropertyTrigger(character.getID(), Position.class, "Y"),
                    new Action(healthScriptPath));
            eventSystem.registerEvent(new KeyTrigger("D"), new Action(moveRightScriptPath));
            eventSystem.registerEvent(new KeyTrigger("A"), new Action(moveLeftScriptPath));
            eventSystem.registerEvent(new KeyTrigger("W"), new Action(jumpScriptPath));
            eventSystem.saveEventsToFile("eventtest.xml");
            EventFileWriter w = new EventFileWriter();
            w.addEvent(KeyTrigger.class.toString().split(" ")[1], "A", moveLeftScriptPath);
            w.addEvent(KeyTrigger.class.toString().split(" ")[1], "D", moveRightScriptPath);
            w.writeEventsToFile("eventTest2.xml");
        } else {
            character = new XMLReader<IEntity>().readSingleFromFile("character.xml");
            universe.addEntity(character);
            eventSystem.readEventsFromFilePath("eventtest.xml");
        }
        charSpr = drawCharacter(character);
    }

    public void step(double dt) {
        physics.update(universe, dt);
        // inputSystem.processInputs();
        eventSystem.updateInputs();
        // moveEntity(character, 1);
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
>>>>>>> 4b37dbfbef7ed6e05fe66b5a93ac860c3b44c630
}
