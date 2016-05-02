package testing.games;

import events.Action;
import events.KeyTrigger;
import groovy.lang.GroovyShell;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.visual.Sprite;
import model.entity.Entity;
import api.IEntity;
import api.IEventSystem;
import api.IGameScript;
import api.ILevel;
import api.IPhysicsEngine;
import api.ISystemManager;
import javafx.scene.input.KeyEvent;
import java.util.Map;
import java.util.HashMap;

public class ShootingGame implements IGameScript {

    private final String player1ProjectileName = "water";
    private final String player1ProjectileImage = "resources/images/squirtleSmall.png";
    private final String player2ProjectileName = "fire";
    private final String player2ProjectileImage = "resources/images/charmanderSmall.png";
    private final String backgroundImage = "resources/images/dragontemple.gif";
    private final String playerImage1 = "resources/images/blastoise.png";
    private final String playerImage2 =  "resources/images/charizard.png";
    private final String moveEntityScript = "resources/providedScripts/MoveEntity.groovy";
    private final String stopEntityScript = "resources/providedScripts/StopPerson.groovy";
    private final String fireProjectileScript = "resources/providedScripts/fireProjectile.groovy";
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
        setPlayer1();
        setPlayer2();
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

    private void setPlayer1() {
        IEntity player = new Entity("player1");
        Sprite sprite = new Sprite(playerImage1);
        sprite.setImageHeight(100);
        sprite.setImageWidth(100);
        player.addComponent(sprite);
        player.addComponent(new Position(0,500));
        player.addComponent(new Velocity(0,0));
        universe.addEntity(player);
        Map<String, Object> map = new HashMap<>();

        map.clear();
        map.put("entityName", player.getName());
        map.put("velocityX", 0);
        map.put("velocityY", -50);
        events.registerEvent(new KeyTrigger("A", KeyEvent.KEY_PRESSED), new Action(moveEntityScript, map));

        map.clear();
        map.put("entityName", player.getName());
        map.put("velocityX", 0);
        map.put("velocityY", 50);
        events.registerEvent(new KeyTrigger("Z", KeyEvent.KEY_PRESSED), new Action(moveEntityScript, map));

        map.clear();
        map.put("entityName", player1ProjectileName);
        map.put("playerName", player.getName());
        map.put("direction", "east");
        map.put("spritePath", player1ProjectileImage);
        events.registerEvent(new KeyTrigger("S", KeyEvent.KEY_RELEASED), new Action(fireProjectileScript, map));
    }

    private void setPlayer2() {
        IEntity player = new Entity("player2");
        Sprite sprite = new Sprite(playerImage2);
        sprite.setImageHeight(100);
        sprite.setImageWidth(100);
        player.addComponent(sprite);
        player.addComponent(new Position(750,500));
        player.addComponent(new Velocity(0,0));
        universe.addEntity(player);
        Map<String, Object> map = new HashMap<>();

        map.clear();
        map.put("entityName", player.getName());
        map.put("velocityX", 0);
        map.put("velocityY", -50);
        events.registerEvent(new KeyTrigger("K", KeyEvent.KEY_PRESSED), new Action(moveEntityScript, map));

        map.clear();
        map.put("entityName", player.getName());
        map.put("velocityX", 0);
        map.put("velocityY", 50);
        events.registerEvent(new KeyTrigger("M", KeyEvent.KEY_PRESSED), new Action(moveEntityScript, map));

        map.clear();
        map.put("entityName", player2ProjectileName);
        map.put("playerName", player.getName());
        map.put("direction", "west");
        map.put("spritePath", player2ProjectileImage);
        events.registerEvent(new KeyTrigger("J", KeyEvent.KEY_RELEASED), new Action(fireProjectileScript, map));
    }





}
