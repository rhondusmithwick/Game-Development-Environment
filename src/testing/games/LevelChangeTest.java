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
import model.component.physics.Collision;
import model.component.physics.Gravity;
import model.component.physics.Mass;
import model.component.physics.RestitutionCoefficient;
import model.component.visual.AnimatedSprite;
import model.component.visual.Sprite;
import model.core.SystemManager;
import model.entity.Entity;
import model.entity.Level;
import model.physics.PhysicsEngine;
import api.IEntity;
import api.IEventSystem;
import api.ILevel;
import api.ISystemManager;
import datamanagement.XMLReader;
import events.Action;
import events.KeyTrigger;
import events.PropertyTrigger;

public class LevelChangeTest {
    private static final String CHAR = "Carolyn";
	private static final String BLASTOISE = "Ano";
	private static final String PLATFORM = "Ani";
	public static final String TITLE = "Ani's and Carolyn's game";
    public static final int KEY_INPUT_SPEED = 5;
    private static Group root;
    private ISystemManager manager;
    private ILevel level1;
    private ILevel level2;
    private Scene myScene;
    private final String imagePath1 = "resources/images/marioplatform.jpeg";
    private final String imagePath2 = "resources/images/charizard.png";
    private final String imagePath3 = "resources/images/blastoise.png";
    private final String changeLevelScript = "resources/providedScripts/ChangeLevelScript.groovy";
    private final String moveEntityScript = "resources/providedScripts/MoveEntity.groovy";
    private final String stopScript = "resources/providedScripts/StopPerson.groovy";
    private final String jumpScript = "resources/providedScripts/jumpScript.groovy";
    private final String stopOnCollisionScript = "resources/providedScripts/StopOnCollision.groovy";

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
        level1.serialize("level1.xml");
        level2 = createLevel2();
        level2.serialize("level2.xml");
        manager = new SystemManager(myScene, level1);
        manager.play();
        return myScene;
    }

    private ILevel createLevel2() {
		ILevel level = new Level();
		level.addEntity(createEntity(PLATFORM, imagePath2, false, 100, 200));
		return level;
	}

	private ILevel createLevel1() {
		ILevel level = new Level();
		IEntity platform = createEntity(PLATFORM, imagePath1, false, 0, 300, 100, 1000);
		//platform.removeComponent(Velocity.class);
		level.addEntity(platform);
		IEntity charizard = createEntity(CHAR, imagePath2, true, 200, 100, 100, 100);
		level.addEntity(charizard);
		IEntity blastoise = createEntity(BLASTOISE, imagePath3, true, 70, 100, 100, 100);
		level.addEntity(blastoise);
		Map<String, Object> map = new HashMap<>();
		
		map.clear();
		map.put("nextLevelPath", "level2.xml");
		level.getEventSystem().registerEvent(new KeyTrigger("P"), new Action(changeLevelScript, map));
		
		map.clear();
		map.put("entityName", PLATFORM);
		map.put("velocityX", 0);
		map.put("velocityY", 0);
		level.getEventSystem().registerEvent(new PropertyTrigger(platform.getID(), Velocity.class, "XVelocity"), new Action(moveEntityScript, map));
		
		map.clear();
		map.put("entityName", PLATFORM);
		map.put("velocityX", 0);
		map.put("velocityY", 0);
		level.getEventSystem().registerEvent(new PropertyTrigger(platform.getID(), Velocity.class, "YVelocity"), new Action(moveEntityScript, map));
		
		map.clear();
		map.put("entityName", CHAR);
		map.put("velocityX", -100);
		map.put("velocityY", 0);
		level.getEventSystem().registerEvent(new KeyTrigger("J", KeyEvent.KEY_RELEASED), new Action(moveEntityScript, map));
		
		map.clear();
		map.put("entityName", CHAR);
		map.put("velocityX", 100);
		map.put("velocityY", 0);
		level.getEventSystem().registerEvent(new KeyTrigger("L", KeyEvent.KEY_RELEASED), new Action(moveEntityScript, map));
		
		/*map.clear();
		map.put("entityName", CHAR);
		level.getEventSystem().registerEvent(new KeyTrigger("J", KeyEvent.KEY_RELEASED), new Action(stopScript, map));*/
		
		/*map.clear();
		map.put("entityName", CHAR);
		level.getEventSystem().registerEvent(new KeyTrigger("L", KeyEvent.KEY_RELEASED), new Action(stopScript, map));*/
		
		map.clear();
		map.put("entityName", CHAR);
		map.put("stopID", platform.getID());
		level.getEventSystem().registerEvent(new PropertyTrigger(charizard.getID(), Collision.class, "CollidingIDs"), new Action(stopOnCollisionScript, map));
		
		map.clear();
		map.put("entityName", CHAR);
		map.put("velocityX", 0);
		map.put("velocityY", -200);
		level.getEventSystem().registerEvent(new KeyTrigger("I", KeyEvent.KEY_RELEASED), new Action(jumpScript, map));
		
		
		map.clear();
		map.put("entityName", BLASTOISE);
		map.put("velocityX", -100);
		map.put("velocityY", 0);
		level.getEventSystem().registerEvent(new KeyTrigger("A", KeyEvent.KEY_RELEASED), new Action(moveEntityScript, map));
		
		map.clear();
		map.put("entityName", BLASTOISE);
		map.put("velocityX", 100);
		map.put("velocityY", 0);
		level.getEventSystem().registerEvent(new KeyTrigger("D", KeyEvent.KEY_RELEASED), new Action(moveEntityScript, map));
		
		/*map.clear();
		map.put("entityName", BLASTOISE);
		level.getEventSystem().registerEvent(new KeyTrigger("A", KeyEvent.KEY_RELEASED), new Action(stopScript, map));
		
		map.clear();
		map.put("entityName", BLASTOISE);
		level.getEventSystem().registerEvent(new KeyTrigger("D", KeyEvent.KEY_RELEASED), new Action(stopScript, map));*/
		
		map.clear();
		map.put("entityName", BLASTOISE);
		map.put("stopID", platform.getID());
		level.getEventSystem().registerEvent(new PropertyTrigger(blastoise.getID(), Collision.class, "CollidingIDs"), new Action(stopOnCollisionScript, map));
		
		map.clear();
		map.put("entityName", BLASTOISE);
		map.put("velocityX", 0);
		map.put("velocityY", -200);
		level.getEventSystem().registerEvent(new KeyTrigger("W", KeyEvent.KEY_RELEASED), new Action(jumpScript, map));
		
		return level;
	}

	private IEntity createEntity(String name, String imagePath, boolean grav, int x, int y) {
		IEntity entity = new Entity(name);
		Sprite sprite = new Sprite(imagePath);
		sprite.getImageView().setLayoutX(x);
		sprite.getImageView().setLayoutX(y);
		Position position = new Position(x,y);
		Collision collision = new Collision(name);
		Mass mass =new Mass();
		RestitutionCoefficient rest = new RestitutionCoefficient();
		if(name.equals(PLATFORM)) {
			mass = new Mass(1500);
			rest = new RestitutionCoefficient(1);
			entity.addComponent(rest);
			entity.addComponent(mass);
		}
		else {
			mass = new Mass(15);
			entity.addComponent(mass);
			rest = new RestitutionCoefficient(1.5);
			entity.addComponent(rest);
		}
		if(grav) {
			Gravity gravity = new Gravity(10);
			entity.addComponent(gravity);
		}
		Velocity velocity = new Velocity(0,0);
		
		entity.addComponent(sprite);
		entity.addComponent(position);
		entity.addComponent(collision);
		entity.addComponent(velocity);
		return entity;
	}
	
	private IEntity createEntity(String name, String imagePath, boolean grav, int x, int y, int height, int width) {
		IEntity entity = createEntity(name, imagePath, grav, x, y);
		entity.getComponent(Sprite.class).setImageHeight(height);
		entity.getComponent(Sprite.class).setImageWidth(width);
		return entity;
	}

	public void step (double dt) {
		root.getChildren().clear();
    	manager.step(dt);
    	manager.getEntitySystem().getAllEntities().stream().forEach(e->drawCharacter(e));
    	//manager.getEntitySystem().getAllEntities().stream().forEach(e->printCollidingIDs(e));
    	//System.out.println();
    }
	
	private void drawCharacter(IEntity character) {
		Sprite imgPath = character.getComponent(Sprite.class);
		ImageView charSprite = imgPath.getImageView();
		charSprite.setLayoutX(character.getComponent(Position.class).getX());
		charSprite.setLayoutY(character.getComponent(Position.class).getY());
		charSprite.setPreserveRatio(true);
		charSprite.setPreserveRatio(true);
		root.getChildren().add(charSprite);
	}
	
	private void printCollidingIDs(IEntity character) {
		Collision collision = character.getComponent(Collision.class);
		System.out.print(character.getName()+":"+collision.getCollidingIDs()+";");
	}

}
