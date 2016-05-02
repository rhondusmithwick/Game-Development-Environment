package testing.games

import events.Action
import events.KeyTrigger
import events.TimeTrigger
import groovy.lang.GroovyShell;
import model.component.movement.Position
import model.component.movement.Velocity
import model.component.visual.Sprite
import model.entity.Entity
import api.IEntity
import api.IEventSystem;
import api.IGameScript
import api.ILevel;
import api.IPhysicsEngine;
import api.ISystemManager;
import javafx.scene.input.KeyEvent;

public class CollectGame implements IGameScript {

	private final String backgroundImage = "resources/images/fallgarden.gif";
	private final String playerImage = "resources/images/blastoise.png";
	private final String moveEntityScript = "resources/providedScripts/MoveEntity.groovy";
	private final String stopEntityScript = "resources/providedScripts/StopPerson.groovy";
	private final String spawnFruit = "resources/providedScripts/spawnFruitScript.groovy";
	private ISystemManager game;
	private ILevel universe;
	private IPhysicsEngine physics;
	private IEventSystem events;
	
	@Override
	public void init(GroovyShell shell, ISystemManager game) {
		this.game = game;
        this.universe = game.getLevel();
        this.physics = game.getLevel().getPhysicsEngine();
        this.events = universe.getEventSystem();
		setBackground();
		setPlayer();
		setFruits(100);
	}

	@Override
	public void update(double dt) {
		universe.update(dt);
	}
	
	private void setBackground() {
		IEntity background = new Entity("background");
		Sprite sprite = new Sprite(backgroundImage);
		sprite.setImageHeight(700);
		sprite.setImageWidth(1000);
		background.addComponent(sprite);
		background.addComponent(new Position(0,0));
		universe.addEntity(background);
	}
	
	private void setPlayer() {
		IEntity player = new Entity("player");
		Sprite sprite = new Sprite(playerImage);
		sprite.setImageHeight(100);
		sprite.setImageWidth(100);
		player.addComponent(sprite);
		player.addComponent(new Position(500,500));
		player.addComponent(new Velocity(0,0));
		universe.addEntity(player);
		Map<String, Object> map = new HashMap<>();
		
		map.clear();
		map.put("entityName", player.getName());
		map.put("velocityX", -50);
		map.put("velocityY", 0);
		events.registerEvent(new KeyTrigger("A", KeyEvent.KEY_PRESSED), new Action(moveEntityScript, map));
		
		map.clear();
		map.put("entityName", player.getName());
		map.put("velocityX", 50);
		map.put("velocityY", 0);
		events.registerEvent(new KeyTrigger("D", KeyEvent.KEY_PRESSED), new Action(moveEntityScript, map));
		
		map.clear();
		map.put("entityName", player.getName());
		map.put("velocityX", 50);
		map.put("velocityY", 0);
		events.registerEvent(new KeyTrigger("A", KeyEvent.KEY_RELEASED), new Action(stopEntityScript, map));
		
		map.clear();
		map.put("entityName", player.getName());
		map.put("velocityX", 50);
		map.put("velocityY", 0);
		events.registerEvent(new KeyTrigger("D", KeyEvent.KEY_RELEASED), new Action(stopEntityScript, map));
	}
	
	private void setFruits(int numFruits) {
		for(int i =0;i<numFruits;i++) {
			double time = Math.random()*50+1;
			int x = (int )(Math.random() * 650);
			Map<String, Object> map = new HashMap<>();
			map.put("positionX", x);
			map.put("fruitName", "fruit");
			events.registerEvent(new TimeTrigger(time), new Action(spawnFruit, map));
		}
	}
}