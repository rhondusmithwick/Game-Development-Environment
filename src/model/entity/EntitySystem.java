package model.entity;

import model.component.base.Component;
import serialization.SerializableReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by rhondusmithwick on 3/30/16.
 *
 * @author Rhondu Smithwick
 */
public class EntitySystem {
    private final Map<Integer, Entity> entities = new HashMap<>();
    private int maxID = 0;

    public Entity createEntity() {
        int ID = getNextAvailableID();
        Entity entity = new Entity(ID);
        maxID++;
        return entity;
    }

    public Entity createEntityFromLoad(String fileName) {
        Entity entity = new SerializableReader<Entity>(fileName).readSingle();
        addEntity(entity);
        return entity;
    }

    public Entity createEntityFromDefault(String defaultFileName) {
        Entity entity = createEntity();
        List<Component> components = new SerializableReader<Component>(defaultFileName).read();
        entity.addComponentList(components);
        return entity;
    }

    public void addEntity(Entity entity) {
        entities.put(entity.getID(), entity);
    }

    public Entity getEntity(int ID) {
        if (entities.containsKey(ID)) {
            return entities.get(ID);
        }
        return null;
        // throw new EntityNotFoundException();
    }

    public void killEntity(int ID) {
        entities.remove(ID);
    }

    private int getNextAvailableID() {
        return maxID + 1;
    }

}
