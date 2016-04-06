package model.entity;

import com.google.common.collect.Maps;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Collection;
import java.util.Map;

/**
 * Implementation of an entity system.
 *
 * @author Rhondu Smithwick
 * @see IEntitySystem
 */
public class EntitySystem implements IEntitySystem {

    @XStreamAlias("entities")
    private final Map<Integer, IEntity> entities = Maps.newLinkedHashMap();

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
