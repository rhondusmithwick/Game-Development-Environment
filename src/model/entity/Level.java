package model.entity;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.google.common.collect.Maps;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import api.IComponent;
import api.IEntity;
import api.IEventSystem;
import api.IGameScript;
import api.ILevel;
import api.IPhysicsEngine;
import api.ISystemManager;
import events.EventSystem;
import groovy.lang.GroovyShell;
import model.physics.PhysicsEngine;
import view.enums.DefaultStrings;

/**
 * Implementation of a Level. This implementation is focused on the IDs. It
 * spawns entities based on the next available ID and adds them to the system.
 *
 * @author Rhondu Smithwick
 */
public class Level implements ILevel {

	/**
	 * The entities in this system.
	 */
	@XStreamAlias("entities")
	private final Map<String, IEntity> entities = Maps.newLinkedHashMap();
	private String name;
	private Map<String, String> metadata = Maps.newLinkedHashMap();
	private IEventSystem eventSystem = new EventSystem(this);
	private IPhysicsEngine physics = new PhysicsEngine();
	private String eventSystemPath;
	private ResourceBundle scriptLocs = ResourceBundle.getBundle(DefaultStrings.SCRIPTS_LOC.getDefault());
	private List<IGameScript> gameScripts = new ArrayList<IGameScript>();

	public Level() {
		this("");
	}

	public Level(String name) {
		this.name = name;
	}

	@Override
	public IEntity createEntity() {
		IEntity entity = new Entity();
		addEntity(entity);
		return entity;
	}

	@Override
	public IEntity addEntity(IEntity entity) {
		return entities.put(entity.getID(), entity);
	}

	@Override
	public IEntity getEntity(String i) {
		return entities.get(i);
	}

	@Override
	public Collection<IEntity> getAllEntities() {
		return entities.values();
	}

	@Override
	public boolean containsID(String id) {
		return entities.containsKey(id);
	}

	@Override
	public boolean removeEntity(String id) {
		if (containsID(id)) {
			IEntity entity = entities.remove(id);
			entity.getAllComponents().stream().forEach(IComponent::removeBindings);
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;

	}

	@Override
	public boolean isEmpty() {
		return this.getAllEntities().isEmpty();
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
		String returnMessage = "";
		String key = "script";
		if (this.metadata.containsKey(key)) {
			String value = this.metadata.get(key);
			String[] scripts = value.split(",");
			for (String script : scripts) {
				try {
					IGameScript gameScript = (IGameScript) Class.forName(scriptLocs.getString(script)).getConstructor()
							.newInstance();
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
		// physics.update(this, dt);
		for (IGameScript gameScript : gameScripts) {
			gameScript.update(dt);
		}
	}

	@Override
	public IEventSystem getEventSystem() {
		return this.eventSystem;
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

}
