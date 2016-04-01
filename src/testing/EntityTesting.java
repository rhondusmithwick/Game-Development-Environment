package testing;

import model.component.movement.Position;
import model.component.movement.Velocity;
import model.entity.Entity;
import model.entity.EntitySystem;
import serialization.SerializableWriter;

/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
class EntityTesting implements Tester {
    private static final String SERIALIZED_DEFAULT_FILE_NAME = "resources/playerDefault.ser";
    private static final String SERIALIZED_LOAD_FILE_NAME = "resources/plaer.ser";

    private final EntitySystem entitySystem = new EntitySystem();

    public void test() {
        Entity entity = loadDefault();
        new SerializableWriter(SERIALIZED_LOAD_FILE_NAME).write(entity);
        loadFromBuilt();
    }

    private Entity loadDefault() {
        Position position = new Position(50.0, 40.0);
        Velocity velocity = new Velocity(100.0, 10.0);
        new SerializableWriter(SERIALIZED_DEFAULT_FILE_NAME).write(position, velocity);
        Entity entity = entitySystem.createEntityFromDefault(SERIALIZED_DEFAULT_FILE_NAME);
        System.out.println("Entity read from default file: " + entity);
        return entity;
    }

    private void loadFromBuilt() {
        Entity entity = entitySystem.createEntityFromLoad(SERIALIZED_LOAD_FILE_NAME);
        System.out.println("Entity read from load file: " + entity);
    }

    public static void main(String[] args) {
        new EntityTesting().test();
    }

}
