package entitytesting;

/**
 * Created by rhondusmithwick on 3/30/16.
 *
 * @author Rhondu Smithwick
 */
public class Main {

    public static void main(String[] args) {
        EntitySystem entitySystem = new EntitySystem();
        entitySystem.createEntity("player");
        Entity entity = entitySystem.getEntity(1);
        Position position = entity.getComponent(Position.class);
        System.out.println(position);
    }
}
