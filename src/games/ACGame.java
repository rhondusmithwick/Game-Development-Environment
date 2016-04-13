package games;

import events.Action;
import events.EntityAction;
import events.InputSystem;
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
	private final PhysicsEngine physics = new PhysicsEngine(universe);
	private IEntity character;
	private final String IMAGE_PATH = "resources/images/blastoise.png";
	private final String healthScriptPath = "resources/groovyScripts/ACGameTestScript.groovy";
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
        initEngine();
        return myScene;
    }
    
    public void initEngine() { 
    	addCharacter();
    }

	private void addCharacter() {
		character = new Entity("Anolyn");
		character.forceAddComponent(new Health((double) 100), true);
		character.forceAddComponent(new Score((double) 100), true);
		Position pos = new Position(250.0, 250.0);
		character.forceAddComponent(pos, true);
		character.forceAddComponent(new ImagePath(IMAGE_PATH), true);
		//character.forceAddComponent(new Velocity(20.0, 50.0), true);
		//character.getComponent(Position.class).getProperties().get(0).addListener(new ACGameXChangeListener(character));
		//character.getComponent(Position.class).getProperties().get(0).addListener(new EntityAction(character));
    	universe.addEntity(character);
    	character.getComponent(Position.class).getProperties().get(0).addListener(new ACGameXChangeListener(character));
		character.addComponent(new ImagePath(IMAGE_PATH));
    	Action healthAction = getAction(healthScriptPath, character, pos);
    	inputSystem.addEvent("HEALTH_DECREASE", healthAction);
		charSpr = drawCharacter(character);
	}
	
	public void step(double dt) {
		physics.update(universe, dt);
		moveEntity(character, 1);
		refreshDraw(charSpr);
	}
	
	private void refreshDraw(ImageView img) {
		root.getChildren().clear();
		root.getChildren().add(img);
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
		//ImageView testSprite = new ImageView(file.toURI().toString());
	}
	
	private Action getAction(String scriptPath, IEntity entity, IComponent component) {
		String script = null;
		try {
			script = Files.toString(new File(scriptPath), Charsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new EntityAction(script, entity, component);
	}
	
	private void moveEntity(IEntity character, int move) { 
		 Position pos = character.getComponent(Position.class);
		 pos.setX(pos.getX() + move);
	}
}
