package usecases;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import api.IEntity;
import api.IEntitySystem;
import api.IEventSystem;
import api.IPhysicsEngine;
import api.ISystemManager;
import datamanagement.XMLReader;
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
	private IEntitySystem universe = new EntitySystem();
	private IEntitySystem sharedUniverse = new EntitySystem();
	private transient IEventSystem eventSystem;
	private transient IPhysicsEngine physics = new PhysicsEngine();
	private boolean isRunning = true;

	public SystemManager() {
		this.eventSystem = new EventSystem(universe, new InputSystem(universe));
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
		this.eventSystem = new EventSystem(universe, new InputSystem(universe));
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

	@Override
	public File saveEntitySystemToFile() {
		String result = this.universe.serializeToString();
		return new File(result);
	}

	@Override
	public File saveSharedEntitySystemToFile() {
		String result = this.sharedUniverse.serializeToString();
		return new File(result);
	}

	// TODO: merge with XMLReader
	private String fileToString(File file) {
		String s = "";
		try {
			s = Files.toString(file, Charsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Error: SystemManager.fileToString() failed.");
		}
		return s;
	}

	@Override
	public void loadEntitySystemFromFile(File file) {
		String result = this.fileToString(file);
		this.universe = new XMLReader<IEntitySystem>().readSingleFromString(result);
	}

	@Override
	public void loadSharedEntitySystemFromFile(File file) {
		String result = this.fileToString(file);
		this.sharedUniverse = new XMLReader<IEntitySystem>().readSingleFromString(result);
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
}
