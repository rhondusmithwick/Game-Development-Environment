package model.core;

import api.IEntity;
import api.IEntitySystem;
import api.IEventSystem;
import api.ILevel;
import api.ISystemManager;
import datamanagement.XMLReader;
import groovy.lang.GroovyShell;
import javafx.animation.Animation;
import javafx.scene.Scene;
import model.component.visual.AnimatedSprite;
import model.entity.Entity;
import model.entity.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
//import testing.demo.GroovyDemoTest;

/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
public class SystemManager implements ISystemManager {

    private GroovyShell shell = new GroovyShell(); // CANNOT BE SCRIPT ENGINE
    private ILevel universe = new Level();
    private List<ILevel> levelList = new ArrayList<>();
    private ILevel sharedUniverse = new Level();
    private boolean isRunning = false;
    private Scene scene; // TODO: remove

    public SystemManager (Scene scene) {
        this(scene, new Level());
    }

    public SystemManager (Scene scene, ILevel level) {
        this.scene = scene;
        this.universe = level;
        levelList.add(level);
        initLevel();
    }

    @Deprecated
    public SystemManager () {
        this(new Level());
    }

    @Deprecated
    public SystemManager (ILevel level) {
        this.universe = level;
        initLevel();
    }

    private void initLevel () {
        universe.init(shell, this);
        shell.setVariable("game", this);
        shell.setVariable("universe", universe);
        //shell.setVariable("demo", new GroovyDemoTest()); // TODO: remove
    }

    @Override
    public void pauseLoop () {
        this.isRunning = false;
        pauseAnimations();
    }

    private void animationWork (Consumer<Animation> animationConsumer) {
        if (getEntitySystem() != null) {
            getEntitySystem().getAllComponentsOfType(AnimatedSprite.class)
                    .stream().forEach(a -> {
                Animation currentAnimation = a.getCurrentAnimation();
                if (currentAnimation != null) {
                    animationConsumer.accept(currentAnimation);
                }
            });
        }
    }

    private void playAnimations () {
        animationWork(Animation::play);
    }

    private void pauseAnimations () {
        animationWork(Animation::pause);
    }

    @Override
    public void step (double dt) {
        if (this.isRunning) {
        	if(universe.checkIfLevelOver()) {
                loadLevel(universe.getNextLevelPath());
            }
            universe.update(dt);
        }
    }

    @Override
    public IEntitySystem getEntitySystem () {
        return universe.getEntitySystem();
    }

    @Override
    public ILevel getLevel () {
        return this.universe;
    }

    @Override
    public ILevel getSharedLevel () {
        return this.sharedUniverse;
    }

    @Deprecated
    @Override
    public IEventSystem getEventSystem () {
        System.out.println("Events deprecated in SystemManager.");
        System.exit(1);
        return null;
    }

    @Override
    public void play () {
        this.isRunning = true;
        universe.getEventSystem().clearInputs();
        playAnimations();
    }

    // private void readObject(ObjectInputStream in) throws IOException,
    // ClassNotFoundException {
    // in.defaultReadObject();
    // this.eventSystem = new EventSystem(universe);
    // this.physics = new PhysicsEngine();
    // }

    @Override
    public void saveLevel (String filename) {
        this.universe.serialize(filename);
    }

    @Override
    public void saveSharedLevel (String filename) {
        this.sharedUniverse.serialize(filename);
    }

    @Override
    public void loadLevel (String filename) {
        this.universe = new XMLReader<ILevel>().readSingleFromFile(filename);
        initLevel();
    }

    @Override
    public void loadSharedLevel (String filename) {
        this.sharedUniverse = new XMLReader<ILevel>().readSingleFromFile(filename);
    }

    private IEntity[] idsToEntityArray (ILevel system, String... ids) {
        IEntity[] entities = new Entity[ids.length];
        for (int i = 0; i < entities.length; i++) {
            entities[i] = system.getEntity(ids[i]);
        }
        return entities;
    }

    @Override
    public void moveEntitiesToMainSystem (IEntity... entities) {
        for (IEntity e : entities) {
            this.sharedUniverse.removeEntity(e.getID());
            this.universe.addEntity(e);
        }
    }

    @Override
    public void moveEntitiesToMainSystem (String... ids) {
        this.moveEntitiesToMainSystem(this.idsToEntityArray(this.sharedUniverse, ids));
    }

    @Override
    public void moveEntitiesToSharedSystem (IEntity... entities) {
        for (IEntity e : entities) {
            this.universe.removeEntity(e.getID());
            this.sharedUniverse.addEntity(e);
        }
    }

    @Override
    public void moveEntitiesToSharedSystem (String... ids) {
        this.moveEntitiesToSharedSystem(this.idsToEntityArray(this.universe, ids));
    }

    @Override
    public GroovyShell getShell () {
        return this.shell;
    }
    
    @Override
    public Scene getScene() {
    	return scene;
    }

}