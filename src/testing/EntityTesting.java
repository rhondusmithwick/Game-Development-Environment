package testing;

import model.component.IComponent;
import model.entity.IEntity;
import model.entity.IEntitySystem;
import datamanagement.XMLWriter;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.entity.EntitySystem;


/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
class EntityTesting implements Tester {
    private static final String DEFAULT_FILE_NAME = "resources/playerDefault.xml";
    private static final String LOAD_FILE_NAME = "resources/player.xml";

    private final IEntitySystem entitySystem = new EntitySystem();

    public static void main(String[] args) {
        new EntityTesting().test();
    }

    @Override
    public void test() {
        IEntity entity = loadDefault();
        new XMLWriter<IEntity>().writeToFile(LOAD_FILE_NAME, entity);
        loadFromBuilt();
    }

    private IEntity loadDefault() {
        System.out.println("DEFAULT:");
        Position position = new Position(50.0, 40.0);
        Velocity velocity = new Velocity(100.0, 10.0);
        new XMLWriter<IComponent>().writeToFile(DEFAULT_FILE_NAME, position, velocity);
        IEntity entity = entitySystem.createEntityFromDefault(DEFAULT_FILE_NAME);
        System.out.println("Entity read from default file: " + entity);
        return entity;
    }

    private void loadFromBuilt() {
        System.out.println("PRELOADED:");
        IEntity entity = entitySystem.createEntityFromLoad(LOAD_FILE_NAME);
        entity.getComponent(Velocity.class).setSpeed(5000);
        System.out.println("Entity read from load file: " + entity);
        System.out.println("Testing get Component: " + entity.getComponent(Position.class));
    }

}
