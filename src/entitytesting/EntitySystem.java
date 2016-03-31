package entitytesting;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rhondusmithwick on 3/30/16.
 *
 * @author Rhondu Smithwick
 */
public class EntitySystem {
    private final int maxID = 0;
    private final Map<Integer, Entity> entities = new HashMap<>();
    private final Map<Class<? extends Component>, Map<Entity, Component>> components = new HashMap<>();

    public int getNextAvailableID() {
        return maxID + 1;
    }

    public Entity createEntity() {
        int ID = getNextAvailableID();
        Entity entity = new Entity(ID, this);
        entities.put(ID, entity);
        return entity;
    }

    public Entity getEntity(int ID) {
        if (entities.containsKey(ID)) {
            return entities.get(ID);
        }
        return null;
//        throw new EntityNotFoundException();
    }

    public void killEntity(int ID) {
        Entity entity = entities.remove(ID);
        components.values().stream().forEach(map -> map.remove(entity));
    }

    public <T extends Component> void addComponent(Component component,  Entity entity) {
        Class<? extends Component> theClass = component.getClass();
        if (!components.containsKey(theClass)) {
            components.put(theClass, new HashMap<>());
        }
        components.get(theClass).put(entity, component);

    }

    public <T extends Component> T getComponent(Entity entity, Class<T> componentClass) {
        Map<Entity, Component> componentStorage = components.get(componentClass);
        T queriedComponent = (T) componentStorage.get(entity); // nasty
        if (queriedComponent == null) {
//            throw new NoComponentFoundException();
        }
        return queriedComponent;
    }

}