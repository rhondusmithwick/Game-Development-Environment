package testing;

import api.IComponent;
import api.IEntity;
import api.ILevel;
import api.ILevel;
import datamanagement.XMLWriter;
import model.component.character.Attack;
import model.component.character.Defense;
import model.component.character.Health;
import model.component.character.Score;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.physics.Mass;
import model.entity.Entity;
import model.entity.Level;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
class EntityTesting implements Tester {
    private static final String DEFAULT_FILE_NAME = "resources/savedComponents/playerDefault.xml";
    private static final String LOAD_FILE_NAME = "resources/savedEntities/player.xml";

    private final ILevel entitySystem = new Level();

    public static void main(String[] args) {
        new EntityTesting().test();
    }

    @Override
    public void test() {
        testLoadSpecs();
        IEntity entity = loadDefault();
        new XMLWriter<IEntity>().writeToFile(LOAD_FILE_NAME, entity);
        loadFromBuilt();
    }

    private void testLoadSpecs() {
        Map<Class<? extends IComponent>, Integer> specs = getSpecsTestMap();
        IEntity entity1 = new Entity();
        entity1.loadSpecsFromPropertiesFile("templates/player");
        if (specs.equals(entity1.getSpecs())) System.out.println("SPecs worked");
    }

    private Map<Class<? extends IComponent>, Integer> getSpecsTestMap() {
        Map<Class<? extends IComponent>, Integer> specs = new HashMap<>();
        List<Class<? extends IComponent>> classes = Arrays.asList(Attack.class, Defense.class,
                Health.class, Mass.class,
                Position.class,
                Score.class, Velocity.class);
        for (Class<? extends IComponent> c: classes) {
            specs.put(c, 1);
        }
        return specs;
    }

    private IEntity loadDefault() {
        System.out.println("DEFAULT:");
        Position position = new Position(50.0, 40.0);
        Velocity velocity = new Velocity(100.0, 10.0);

        IComponent hey = new Position(50.0, 80.0);
        System.out.println(hey.getClass().getSimpleName());
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
