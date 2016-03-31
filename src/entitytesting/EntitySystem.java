package entitytesting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by rhondusmithwick on 3/30/16.
 *
 * @author Rhondu Smithwick
 */
public class EntitySystem {
    private final int maxID = 0;
    private final Map<Integer, Entity> entities = new HashMap<>();
    private final ComponentFactory componentFactory = new ComponentFactory();

    public int getNextAvailableID() {
        return maxID + 1;
    }

    public Entity createEntity(String defaultType) {
        int ID = getNextAvailableID();
        Entity entity = new Entity(ID, this);
        entities.put(ID, entity);
        if (!Objects.equals(defaultType, "")) {
            List<Component> components = componentFactory.readFromPropertyFile(defaultType);
            entity.addComponent(components);
        }
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
        entities.remove(ID);
    }


}