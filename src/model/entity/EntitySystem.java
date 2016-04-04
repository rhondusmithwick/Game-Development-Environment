package model.entity;

import api.IEntity;
import api.IEntitySystem;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rhondusmithwick on 4/3/16.
 *
 * @author Rhondu Smithwick
 */
public class EntitySystem implements IEntitySystem {

    @XStreamAlias("entities")
    private final Map<Integer, IEntity> entities = new HashMap<>();

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
