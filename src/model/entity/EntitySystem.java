package model.entity;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import api.IComponent;
import api.IEntity;
import api.IEntitySystem;
import api.IEventSystem;
import api.IPhysicsEngine;
import events.EventSystem;
import model.physics.PhysicsEngine;

/**
 * Implementation of an entity system. This implementation is focused on the
 * IDs. It spawns entities based on the next available ID and adds them to the
 * system.
 *
 * @author Rhondu Smithwick
 */
public class EntitySystem implements IEntitySystem {

	/**
	 * The entities in this system.
	 */
	@XStreamAlias("entities")
	private final Map<String, IEntity> entities = Maps.newLinkedHashMap();
	private String name;
	private Map<String, String> metadata = Maps.newLinkedHashMap();
	private IEventSystem eventSystem = new EventSystem(this, null); // TODO:
																	// update
	private IPhysicsEngine physics = new PhysicsEngine();

	public EntitySystem() {
		this("");
	}

	public EntitySystem(String name) {
		this.name = name;
	}

	@Override
	public IEntity createEntity() {
		Entity entity = new Entity();
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
	public void setName(String name) {
		this.name = name;

	}

	@Override
	public String getName() {
		return this.name;
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
	public void addMetadata(String key, String value) {
		this.metadata.put(key, value);
	}

	@Override
	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	@Override
	public void init(List<File> groovyScripts) {
		// TODO: class-load all Groovy scripts (pass in physics too) and call
		// their init() methods
	}

	@Override
	public void update(double dt) {
		// TODO: call the scripts' update() methods
	}

	@Override
	public IEventSystem getEventSystem() {
		return this.eventSystem;
	}

	@Override
	public IPhysicsEngine getPhysicsEngine() {
		return this.physics;
	}

}
