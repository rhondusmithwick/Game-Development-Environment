package model.entity;

import api.*;

import com.google.common.collect.Maps;

import events.EventSystem;
import groovy.lang.GroovyShell;
import model.physics.PhysicsEngine;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Implementation of a Level. This implementation is focused on the IDs. It
 * spawns entities based on the next available ID and adds them to the system.
 *
 * @author Rhondu Smithwick
 */
public class Level implements ILevel {

	private IEntitySystem universe = new EntitySystem();
	private Map<String, String> metadata = Maps.newLinkedHashMap();
	private IEventSystem eventSystem = new EventSystem(this);
	private IPhysicsEngine physics = new PhysicsEngine();
	private String eventSystemPath;
//	private transient ResourceBundle scriptLocs = ResourceBundle.getBundle(DefaultStrings.SCRIPTS_LOC.getDefault());
	private transient List<IGameScript> gameScripts;

	public Level() {
		this("");
	}

	public Level(String name) {
		universe.setName(name);
	}

	@Override
	public void setName(String name) {
		universe.setName(name);
	}

	@Override
	public String getName() {
		return universe.getName();
	}

	@Override
	public Map<String, String> getMetadata() {
		return this.metadata;
	}

	@Override
	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	@Override
	public void addMetadata(String key, String value) {
		this.metadata.put(key, value);
	}

	@Override
	public String init(GroovyShell shell, ISystemManager game) {
		gameScripts = new ArrayList<>();
		String returnMessage = "";
		String key = "Script"; // TODO: don't hard-code
		if (this.metadata.containsKey(key)) {
			String value = this.metadata.get(key);
			String[] scripts = value.split(",");
			for (String script : scripts) {
				try {
					IGameScript gameScript = (IGameScript) Class.forName(script).getConstructor()
							.newInstance(); // TODO: scriptLocs.getString(script)
					gameScript.init(shell, game);
					gameScripts.add(gameScript);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException
						| ClassNotFoundException e) {
					returnMessage += (e.getMessage() + "\n");
				}
			}
		} else {
			returnMessage = "No scripts";
		}
		return returnMessage;
	}

	@Override
	public void update(double dt) {
		physics.update(this, dt); // TODO: remove
		for (IGameScript gameScript : gameScripts) {
			gameScript.update(dt);
		}
	}

	@Override
	public IEventSystem getEventSystem() {
		return this.eventSystem;
	}


	@Override
	public IEntitySystem getEntitySystem() {
		return this.universe;
	}

	@Override
	public IPhysicsEngine getPhysicsEngine() {
		return this.physics;
	}

	public String getEventSystemPath() {
		return eventSystemPath;
	}

	@Override
	public void setEventSystemPath(String eventSystemPath) {
		this.eventSystemPath = eventSystemPath;
	}

	@Override
	public IEntity createEntity() {
		return universe.createEntity();
	}

	@Override
	public IEntity addEntity(IEntity entity) {
		return universe.addEntity(entity);
	}

	@Override
	public IEntity getEntity(String id) {
		return universe.getEntity(id);
	}

	@Override
	public Collection<IEntity> getAllEntities() {
		return universe.getAllEntities();
	}

	@Override
	public boolean containsID(String id) {
		return universe.containsID(id);
	}

	@Override
	public boolean removeEntity(String id) {
		return universe.removeEntity(id);
	}

	@Override
	public boolean isEmpty() {
		return universe.isEmpty();
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        eventSystem.setUniverse(this);
    }

}
