package entitytesting;

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
        entities.remove(ID);
    }


}