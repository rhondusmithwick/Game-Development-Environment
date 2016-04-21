package model.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import api.IEntity;
import api.IEntitySystem;
import api.IEventSystem;
import api.IPhysicsEngine;
import api.ISystemManager;
import datamanagement.XMLReader;
import datamanagement.XMLWriter;
import events.EventSystem;
import events.InputSystem;
import model.entity.Entity;
import model.entity.EntitySystem;
import model.physics.PhysicsEngine;

/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
public class SystemManager implements ISystemManager {

	private List<String> details;
	private IEntitySystem universe = new EntitySystem();
	private IEntitySystem sharedUniverse = new EntitySystem();
	private transient IEventSystem eventSystem;
	private transient IPhysicsEngine physics = new PhysicsEngine();
	private boolean isRunning = true;

	public SystemManager() {
		this.eventSystem = new EventSystem(universe, new InputSystem());
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
	public IEntitySystem getEntitySystem() {
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
		this.eventSystem = new EventSystem(universe, new InputSystem());
		this.physics = new PhysicsEngine();
	}

	@Override
	public IEntitySystem getSharedEntitySystem() {
		return this.sharedUniverse;
	}

	@Override
	public void saveEntitySystem(String filename) {
		this.universe.serialize(filename);
	}

	@Override
	public void saveSharedEntitySystem(String filename) {
		this.sharedUniverse.serialize(filename);
	}

	@Override
	public void loadEntitySystem(String filename) {
		this.universe = new XMLReader<IEntitySystem>().readSingleFromFile(filename);
	}

	@Override
	public void loadSharedEntitySystem(String filename) {
		this.sharedUniverse = new XMLReader<IEntitySystem>().readSingleFromFile(filename);
	}

	private IEntity[] idsToEntityArray(IEntitySystem system, String... ids) {
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
	public IEntitySystem getUniverse() {
		return universe;
	}

	@Override
	public void setEntitySystem(IEntitySystem system) {
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
