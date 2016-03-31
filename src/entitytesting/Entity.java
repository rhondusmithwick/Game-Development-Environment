import java.io.Serializable;

/**
 * Created by rhondusmithwick on 3/30/16.
 *
 * @author Rhondu Smithwick
 */
public class Entity implements Serializable {
    private final int ID;
    private final EntitySystem entitySystem;

    public Entity(int ID, EntitySystem entitySystem) {
        this.ID = ID;
        this.entitySystem = entitySystem;
    }

    public <T extends Component> T getAs(Class<T> type) {
        return entitySystem.getComponent(this, type);
    }

    public int getID() {
        return ID;
    }
}
