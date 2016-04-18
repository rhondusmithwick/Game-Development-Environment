package games;

import datamanagement.XMLReader;
import events.Action;
import events.EventSystem;
import events.InputSystem;
import events.KeyTrigger;
import events.PropertyTrigger;
import model.component.character.Health;
import model.component.character.Score;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.visual.ImagePath;
import model.entity.EntitySystem;
import model.physics.PhysicsEngine;
import api.IEntity;
import api.IEntitySystem;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.entity.Entity;

public class ACGame {
	
    public final String TITLE = "Ani's and Carolyn's game";
	private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
    public final int KEY_INPUT_SPEED = 5;
	//private final Image BACKGROUND_IMAGE = new Image(getClass().getClassLoader().getResourceAsStream("src/resources/images/grassplatform.jpg"));
    private static Group root;
	private final IEntitySystem universe = new EntitySystem();
	private final InputSystem inputSystem = new InputSystem(universe);
	private final EventSystem eventSystem = new EventSystem(universe, inputSystem);
	private final PhysicsEngine physics = new PhysicsEngine();
	private final String BG_IMAGE_PATH = "resources/images/movingwaterfall.gif";
	private final String IMAGE_PATH = "resources/images/blastoise.png";
	private final String IMAGE_PATH2 = "resources/images/charizard.png";
	private final String healthScriptPath = "resources/groovyScripts/ACGameTestScript.groovy";
	private final String moveRightScriptPath = "resources/groovyScripts/keyInputMoveRight.groovy";
	private final String moveLeftScriptPath = "resources/groovyScripts/keyInputMoveLeft.groovy";
	private final String moveRightScriptPath2 = "resources/groovyScripts/keyInputMoveRight2.groovy";
	private final String moveLeftScriptPath2 = "resources/groovyScripts/keyInputMoveLeft2.groovy";
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
		int var = 0;
		if(var==0) {
			character = new Entity("Anolyn");
			character.forceAddComponent(new Health((double) 100), true);
			character.forceAddComponent(new Score((double) 100), true);
			Position pos = new Position(100.0, 300);
			character.forceAddComponent(pos, true);
			character.forceAddComponent(new ImagePath(IMAGE_PATH), true);
			universe.addEntity(character);
	    	//character.addComponent(new ImagePath(IMAGE_PATH));
			character.serialize("character.xml");
	    	eventSystem.registerEvent(new PropertyTrigger(character.getID(), character.getComponent(Position.class), 0, universe, inputSystem), new Action(healthScriptPath));
			eventSystem.registerEvent(new KeyTrigger("D", universe, inputSystem), new Action(moveRightScriptPath));
			eventSystem.registerEvent(new KeyTrigger("A", universe, inputSystem), new Action(moveLeftScriptPath));
	    	eventSystem.saveEventsToFile("eventtest.xml");
		}
		else {
			character = new XMLReader<IEntity>().readSingleFromFile("character.xml");
			universe.addEntity(character);
			eventSystem.readEventsFromFile("eventtest.xml");
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
		charSprite.xProperty().bind(character.getComponent(Position.class).xProperty());
		charSprite.yProperty().bind(character.getComponent(Position.class).yProperty());
		root.getChildren().add(charSprite);
		return charSprite;
	}
	
	private void moveEntity(IEntity character, int move) { 
		 Position pos = character.getComponent(Position.class);
		 pos.setX(pos.getX() + move);
	}
}
