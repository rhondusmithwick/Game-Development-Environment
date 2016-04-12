package games;

import events.EntityAction;
import events.InputSystem;
import model.component.character.Health;
import model.component.character.Score;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.visual.ImagePath;
import model.entity.Entity;
import model.entity.EntitySystem;
import api.IEntity;
import api.IEntitySystem;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ACGame {
	
    public static final String TITLE = "Ani's and Carolyn's game";
    public static final int KEY_INPUT_SPEED = 5;
    private static final double GROWTH_RATE = 1.1;
    private static final int BOUNCER_SPEED = 30;

    private final IEntitySystem universe = new EntitySystem();
	private final InputSystem inputSystem = new InputSystem();
	private final String IMAGE_PATH = "resources/images/blastoise.png";
    
    private Scene myScene;
    private ImageView myBouncer;
    private Rectangle myTopBlock;
    private Rectangle myBottomBlock;


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
        Group root = new Group();
        // Create a place to see the shapes
        myScene = new Scene(root, width, height, Color.WHITE);
        initEngine();
        return myScene;
    }
    
    public void initEngine() { 
    	addCharacter();
    }

	private void addCharacter() {
		IEntity character = new Entity("Anolyn");
		character.forceAddComponent(new Health((double) 100), true);
		character.forceAddComponent(new Score((double) 100), true);
		Position pos = new Position(250.0, 250.0);
		character.forceAddComponent(pos, true);
		character.forceAddComponent(new ImagePath(IMAGE_PATH), true);
		character.forceAddComponent(new Velocity(20.0, 50.0), true);
		character.getComponent(Position.class).getProperties().get(0).addListener(new EntityAction(character));
    	universe.addEntity(character);
	}
}
