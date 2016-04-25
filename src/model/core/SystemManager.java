package model.core;

import api.IEntity;
import api.IEventSystem;
import api.ILevel;
import api.ISystemManager;
import datamanagement.XMLReader;
import groovy.lang.GroovyShell;
import model.entity.Entity;
import model.entity.Level;
import testing.demo.GroovyDemoTest;

/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
public class SystemManager implements ISystemManager {

	private GroovyShell shell = new GroovyShell(); // CANNOT BE SCRIPT ENGINE
	private ILevel universe = new Level();
	private ILevel sharedUniverse = new Level();
	private boolean isRunning = true;

	public SystemManager() {
		this(new Level());
	}

	public SystemManager(ILevel level) {
		this.universe = level;
//		this.universe.addMetadata("Script", "testing.demo.Pong"); // TODO: remove after GameManager implemented
		universe.init(shell, this);
		shell.setVariable("game", this);
		shell.setVariable("universe", level);
		shell.setVariable("demo", new GroovyDemoTest()); // TODO: remove
	}

	@Override
	public void pauseLoop() {
		this.isRunning = false;
	}

	@Override
	public void step(double dt) {
		if (this.isRunning) {
			universe.update(dt);
			// System.out.println(universe.getAllEntities());
		}
	}

	@Override
	public ILevel getEntitySystem() {
		return this.universe;
	}

	@Deprecated
	@Override
	public IEventSystem getEventSystem() {
		System.out.println("Events deprecated in SystemManager.");
		System.exit(1);
		return null;
	}

	@Override
	public void play() {
		this.isRunning = true;
	}

	// private void readObject(ObjectInputStream in) throws IOException,
	// ClassNotFoundException {
	// in.defaultReadObject();
	// this.eventSystem = new EventSystem(universe);
	// this.physics = new PhysicsEngine();
	// }

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
		universe.init(shell, this);
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
	public GroovyShell getShell() {
		return this.shell;
	}

}
