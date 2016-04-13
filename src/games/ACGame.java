package games;

import events.Action;
import events.EntityAction;
import events.EventSystem;
import events.InputSystem;
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

	private final IEntitySystem universe = new EntitySystem();
	private final EventSystem eventSystem = new EventSystem(universe);
	//private final InputSystem inputSystem = new InputSystem();
	private final PhysicsEngine physics = new PhysicsEngine(universe);
	private IEntity character;
	private final String IMAGE_PATH = "resources/images/blastoise.png";
	private final String healthScriptPath = "resources/groovyScripts/ACGameTestScript.groovy";
    private Scene myScene;
    private Group root;

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
    	eventSystem.registerEvent(new Trigger(character.getComponent(Position.class).getProperties().get(0)), new Action(healthScriptPath));
    	//Action healthAction = getAction(healthScriptPath, character, pos);
    	//inputSystem.addEvent("HEALTH_DECREASE", healthAction);
	}
	
	public void step(double dt) {
		physics.update(universe, dt);
		moveEntity(character, 20);
		draw(character);
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
	
	private void draw(IEntity character) {
		
	}
}
