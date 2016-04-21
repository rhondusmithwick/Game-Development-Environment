package model.core;

import api.IEntity;
import api.IEventSystem;
import api.ILevel;
import api.IPhysicsEngine;
import api.ISystemManager;
import datamanagement.XMLReader;
import datamanagement.XMLWriter;
import events.EventSystem;
import model.entity.Entity;
import model.entity.Level;
import model.physics.PhysicsEngine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
public class SystemManager implements ISystemManager {
	
    private List<String> details;
    private ILevel universe = new Level();
    private ILevel sharedUniverse = new Level();
    private transient IEventSystem eventSystem;
    private transient IPhysicsEngine physics = new PhysicsEngine();
    private boolean isRunning = true;

    public SystemManager() {
        this.eventSystem = new EventSystem(universe);
        // eventSystem.init(universe);
    }

    @Override
    public void pauseLoop() {
        this.isRunning = false;
    }

    @Override
    public void step(double dt) {
        if (this.isRunning) {
            physics.update(universe, dt);
        }
    }

    @Override
    public ILevel getEntitySystem() {
        return this.universe;
    }

    @Override
    public IEventSystem getEventSystem() {
        return this.eventSystem;
    }

    @Override
    public void play() {
        this.isRunning = true;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.eventSystem = new EventSystem(universe);
        this.physics = new PhysicsEngine();
    }

    @Override
    public ILevel getSharedEntitySystem() {
        return this.sharedUniverse;
    }

    @Override
    public void saveLevel(String filename) {
        this.universe.serialize(filename);
    }

    @Override
    public void saveSharedLevel(String filename) {
        this.sharedUniverse.serialize(filename);
    }

    @Override
    public void loadLevel(String filename) {
        this.universe = new XMLReader<ILevel>().readSingleFromFile(filename);
    }

    @Override
    public void loadSharedLevel(String filename) {
        this.sharedUniverse = new XMLReader<ILevel>().readSingleFromFile(filename);
    }

    private IEntity[] idsToEntityArray(ILevel system, String... ids) {
        IEntity[] entities = new Entity[ids.length];
        for (int i = 0; i < entities.length; i++) {
            entities[i] = system.getEntity(ids[i]);
        }
        return entities;
    }

    @Override
    public void moveEntitiesToMainSystem(IEntity... entities) {
        for (IEntity e : entities) {
            this.sharedUniverse.removeEntity(e.getID());
            this.universe.addEntity(e);
        }
    }

    @Override
    public void moveEntitiesToMainSystem(String... ids) {
        this.moveEntitiesToMainSystem(this.idsToEntityArray(this.sharedUniverse, ids));
    }

    @Override
    public void moveEntitiesToSharedSystem(IEntity... entities) {
        for (IEntity e : entities) {
            this.universe.removeEntity(e.getID());
            this.sharedUniverse.addEntity(e);
        }
    }

    @Override
    public void moveEntitiesToSharedSystem(String... ids) {
        this.moveEntitiesToSharedSystem(this.idsToEntityArray(this.universe, ids));
    }

    @Override
    public ILevel getUniverse() {
        return universe;
    }

    @Override
    public void setEntitySystem(ILevel system) {
        this.universe = system;
    }

    @Override
    public void saveSystem(String filename) {
        new XMLWriter<ISystemManager>().writeToFile(filename, this);
    }

    @Override
    public void setDetails(List<String> list) {
        this.details = list;
    }

    @Override
    public List<String> getDetails() {
        return details;
    }
}
