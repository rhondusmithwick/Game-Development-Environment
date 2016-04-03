package model.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import api.IComponent;
import serialization.SerializableReader;


/**
 * Created by rhondusmithwick on 3/30/16.
 *
 * @author Rhondu Smithwick
 */
public class EntitySystem {
    private final Map<Integer, Entity> entities = new HashMap<>();

    private int maxID = 0;

    public Entity createEntity () {
        int ID = getNextAvailableID();
        Entity entity = new Entity(ID);
        maxID++;
        return entity;
    }

    public Entity createEntityFromLoad (String fileName) {
        Entity entity = new SerializableReader<Entity>(fileName).readSingle();
        addEntity(entity);
        return entity;
    }

    public Entity createEntityFromDefault (String defaultFileName) {
        Entity entity = createEntity();
        List<IComponent> components = new SerializableReader<IComponent>(defaultFileName).read();
        entity.addComponentList(components);
        return entity;
    }

    public void addEntity (Entity entity) {
        entities.put(entity.getID(), entity);
    }

    public <T extends IComponent> List<T> getAllComponentsOfType (Class<T> componentType) {
        return entities.values().stream().map(e -> e.getComponentList(componentType))
                .flatMap(List::stream).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public <T extends IComponent> Set<Entity> getEntitiesWithComponentType (Class<T> componentType) {
        Predicate<Entity> hasComponent = (e) -> e.hasComponent(componentType);
        return entities.values().stream().filter(hasComponent).collect(Collectors.toSet());
    }

    public Entity getEntity (int ID) {
        if (entities.containsKey(ID)) {
            return entities.get(ID);
        }
        return null;
        // throw new EntityNotFoundException();
    }

    public void killEntity (int ID) {
        entities.remove(ID);
    }

    private int getNextAvailableID () {
        return maxID + 1;
    }

}
