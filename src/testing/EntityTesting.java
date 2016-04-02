package testing;

import model.component.base.Component;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.entity.Entity;
import model.entity.EntitySystem;
import serialization.XMLWriter;

/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
class EntityTesting implements Tester {
    private static final String DEFAULT_FILE_NAME = "resources/playerDefault.xml";
    private static final String LOAD_FILE_NAME = "resources/player.xml";

    private final EntitySystem entitySystem = new EntitySystem();

    public static void main(String[] args) {
        new EntityTesting().test();
    }

    public void test() {
        Entity entity = loadDefault();
        new XMLWriter<Entity>().writeToFile(LOAD_FILE_NAME, entity);
        loadFromBuilt();
    }

    private Entity loadDefault() {
        System.out.println("DEFAULT:");
        Position position = new Position(50.0, 40.0);
        Velocity velocity = new Velocity(100.0, 10.0);
        new XMLWriter<Component>().writeToFile(DEFAULT_FILE_NAME, position, velocity);
        Entity entity = entitySystem.createEntityFromDefault(DEFAULT_FILE_NAME);
        System.out.println("Entity read from default file: " + entity);
        return entity;
    }

    private void loadFromBuilt() {
        System.out.println("PRELOADED:");
        Entity entity = entitySystem.createEntityFromLoad(LOAD_FILE_NAME);
        System.out.println("Entity read from load file: " + entity);
        System.out.println("Testing get Component: " + entity.getComponent(Position.class));
    }

}
