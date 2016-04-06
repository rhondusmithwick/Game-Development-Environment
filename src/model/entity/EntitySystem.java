package model.entity;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Implementation of an entity system. This implementation is focused on the
 * IDs. It spawns entities based on the nextavilable ID and adds them to the
 * sysstem.
 *
 * @author Rhondu Smithwick
 */
public class EntitySystem implements IEntitySystem {

	/**
	 * The entities in this system.
	 */
	@XStreamAlias("entities")
	private final Map<Integer, IEntity> entities = Maps.newLinkedHashMap();

	/**
	 * The maxID of this sytem.
	 */
	@XStreamAlias("maxID")
	private int maxID = 0;

	@Override
	public IEntity createEntity() {
		int ID = getNextAvailableID();
		Entity entity = new Entity(ID);
		maxID++;
		addEntity(entity);
		return entity;
	}

	@Override
	public IEntity addEntity(IEntity entity) {
		return entities.put(entity.getID(), entity);
	}

	@Override
	public IEntity getEntity(int id) {
		return entities.get(id);
	}

	@Override
	public Collection<IEntity> getAllEntities() {
		return entities.values();
	}

	@Override
	public boolean containsID(int id) {
		return entities.containsKey(id);
	}

	@Override
	public boolean removeEntity(int id) {
		if (containsID(id)) {
			entities.remove(id);
			return true;
		}
		return false;
	}

	@Override
	public int getNextAvailableID() {
		return maxID + 1;
	}

}
