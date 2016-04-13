package model.entity;

import java.util.Collection;
import java.util.Map;

import api.IEntity;
import api.IEntitySystem;
import com.google.common.collect.Maps;
import com.thoughtworks.xstream.annotations.XStreamAlias;

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

	public EntitySystem(){
		this("");
	}

	public EntitySystem(String name){
		this.name=name;
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
			entities.remove(id);
			return true;
		}
		return false;
	}

	@Override
	public void setName(String name) {
		this.name=name;
		
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean isEmpty() {
		return this.getAllEntities().isEmpty();
	}

}
