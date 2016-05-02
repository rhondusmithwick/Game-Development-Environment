package testing.games;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import model.component.character.Health;
import model.component.character.Score;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.physics.Gravity;
import model.component.visual.AnimatedSprite;
import model.component.visual.Sprite;
import model.core.SystemManager;
import model.entity.Entity;
import model.entity.Level;
import model.physics.PhysicsEngine;
import model.physics.RealisticVelocityCalculator;
import api.ICollisionVelocityCalculator;
import api.IEntity;
import api.IEventSystem;
import api.ILevel;
import api.ISystemManager;
import datamanagement.XMLReader;
import events.Action;
import events.KeyTrigger;
import events.PropertyTrigger;

public class LevelChangeTest {
    public static final String TITLE = "Ani's and Carolyn's game";
    public static final int KEY_INPUT_SPEED = 5;
    private static Group root;
    private ISystemManager manager;
    private ILevel level1;
    private ILevel level2;
    private Scene myScene;
    private final String imagePath1 = "resources/images/blastoise.png";
    private final String imagePath2 = "resources/images/charizard.png";
    private final String changeLevelScript = "resources/providedScripts/ChangeLevelScript.groovy";

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
        level1 = createLevel1();
       // level1.serialize("level1.xml");
        level2 = createLevel2();
        level2.serialize("level2.xml");
        manager = new SystemManager(myScene, level1);
        return myScene;
    }

    private ILevel createLevel2() {
		ILevel level = new Level();
		level.addEntity(createEntity("Ani", imagePath2, 100, 200));
		return level;
	}

	private ILevel createLevel1() {
		ILevel level = new Level();
		level.addEntity(createEntity("Carolyn", imagePath1, 100, 200));
		Map<String, Object> map = new HashMap<>();
		map.put("levelPath", "level2.xml");
		level.getEventSystem().registerEvent(new KeyTrigger("D"), new Action(changeLevelScript, map));
		return level;
	}

	private IEntity createEntity(String name, String imagePath, int x, int y) {
		IEntity entity = new Entity(name);
		Sprite sprite = new Sprite(imagePath);
		sprite.getImageView().setLayoutX(x);
		sprite.getImageView().setLayoutX(y);
		entity.addComponent(sprite);
		return entity;
	}

	public void step (double dt) {
		root.getChildren().clear();
    	manager.step(dt);
    	manager.getEntitySystem().getAllEntities().stream().forEach(e->drawCharacter(e));
    }
	
	private void drawCharacter(IEntity character) {
		Sprite imgPath = character.getComponent(Sprite.class);
		ImageView charSprite = imgPath.getImageView();
//		charSprite.setLayoutX(character.getComponent(Position.class).getX());
//		charSprite.setLayoutY(character.getComponent(Position.class).getY());
//		charSprite.setPreserveRatio(true);
//		charSprite.setPreserveRatio(true);
		root.getChildren().add(charSprite);
	}

}
