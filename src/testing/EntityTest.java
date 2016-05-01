package testing;

import api.IComponent;
import api.IEntity;
import model.component.character.Attack;
import model.component.character.Defense;
import model.component.character.Health;
import model.component.character.Score;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.physics.Mass;
import model.entity.Entity;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by rhondusmithwick on 4/8/16.
 *
 * @author Rhondu Smithwick
 */
public class EntityTest {
//    private static final String DEFAULT_FILE_NAME = "resources/savedComponents/playerDefault.xml";
//    private static final String LOAD_FILE_NAME = "resources/savedEntities/player.xml";
//
//    private final IEntitySystem entitySystem = new Level();


    @Before
    public void setUP () {
    }

    @Test
    public void loadSpecsTest () {
        Map<Class<? extends IComponent>, Integer> specs = getSpecsTestMap();
        IEntity entity1 = new Entity();
        entity1.loadSpecsFromPropertiesFile("templates/player");
        assertEquals(entity1.getSpecs(), specs);
    }

    private Map<Class<? extends IComponent>, Integer> getSpecsTestMap () {
        Map<Class<? extends IComponent>, Integer> specs = new HashMap<>();
        List<Class<? extends IComponent>> classes = Arrays.asList(Attack.class, Defense.class,
                Health.class, Mass.class,
                Position.class,
                Score.class, Velocity.class);
        for (Class<? extends IComponent> c : classes) {
            specs.put(c, 1);
        }
        return specs;
    }


}
