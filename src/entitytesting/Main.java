package entitytesting;

import entitytesting.Entity;
import entitytesting.EntitySystem;

/**
 * Created by rhondusmithwick on 3/30/16.
 *
 * @author Rhondu Smithwick
 */
public class Main {

    public static void main(String[] args) {
        EntitySystem entitySystem = new EntitySystem();
        entitySystem.createEntity();
        Entity entity = entitySystem.getEntity(1);
        entitySystem.addComponent(new Position(50, 30),entity);
        Position position = entitySystem.getComponent(entity, Position.class);
        System.out.println(position);
    }
}
