package model.core;

import api.IEntity;
import api.IEntitySystem;
import api.IEventSystem;
import api.ILevel;
import api.ISystemManager;
import datamanagement.XMLReader;
import groovy.lang.GroovyShell;
import javafx.scene.Scene;
import model.component.movement.Position;
import model.entity.Entity;
import model.entity.Level;

import java.util.List;
//import testing.demo.GroovyDemoTest;

/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
public class SystemManager implements ISystemManager {

    private final GroovyShell shell = new GroovyShell(); // CANNOT BE SCRIPT ENGINE
    private ILevel level = new Level();
    private ILevel sharedUniverse = new Level();
    private boolean isRunning = false;
    private Scene scene; // TODO: remove

    public SystemManager (Scene scene) {
        this(scene, new Level());
    }

    public SystemManager (Scene scene, ILevel level) {
        this.scene = scene;
        this.level = level;
        initLevel();
    }

    @Deprecated
    public SystemManager () {
        this(new Level());
    }

    @Deprecated
    public SystemManager (ILevel level) {
        this.level = level;
        initLevel();
    }

    private void initLevel () {
        System.out.println(level.init(shell, this, scene)); // TODO: remove
        shell.setVariable("game", this);
        shell.setVariable("level", this.getLevel());
        shell.setVariable("level", this.getEntitySystem());
    }

    @Override
    public void pauseLoop () {
        this.isRunning = false;
    }

    @Override
    public void step (double dt) {
        if (this.isRunning) {
            if (level.checkIfLevelOver()) {
                loadLevel(level.getNextLevelPath());
            }
            level.update(dt);
            List<IEntity> entities = level.getAllEntities();
            for (IEntity e : entities) {
                System.out.print(e.getComponent(Position.class).getY());
            }
            System.out.println();
        }
    }

    @Override
    public IEntitySystem getEntitySystem () {
        return level.getEntitySystem();
    }

    @Override
    public ILevel getLevel () {
        return this.level;
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
        level.getEventSystem().clearInputs();
    }

    // private void readObject(ObjectInputStream in) throws IOException,
    // ClassNotFoundException {
    // in.defaultReadObject();
    // this.eventSystem = new EventSystem(universe);
    // this.physics = new PhysicsEngine();
    // }

    @Override
    public void saveLevel (String filename) {
        this.level.serialize(filename);
    }

    @Override
    public void saveSharedLevel (String filename) {
        this.sharedUniverse.serialize(filename);
    }

    @Override
    public void loadLevel (String filename) {
        this.level = new XMLReader<ILevel>().readSingleFromFile(filename);
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
            this.level.addEntity(e);
        }
    }

    @Override
    public void moveEntitiesToMainSystem (String... ids) {
        this.moveEntitiesToMainSystem(this.idsToEntityArray(this.sharedUniverse, ids));
    }

    @Override
    public void moveEntitiesToSharedSystem (IEntity... entities) {
        for (IEntity e : entities) {
            this.level.removeEntity(e.getID());
            this.sharedUniverse.addEntity(e);
        }
    }

    @Override
    public void moveEntitiesToSharedSystem (String... ids) {
        this.moveEntitiesToSharedSystem(this.idsToEntityArray(this.level, ids));
    }

    @Override
    public GroovyShell getShell () {
        return this.shell;
    }

}
