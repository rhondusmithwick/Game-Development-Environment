package testing;

import api.IEntity;
import api.IEntitySystem;
import events.Action;
import model.component.movement.Position;
import model.entity.Entity;
import model.entity.EntitySystem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by rhondusmithwick on 4/20/16.
 *
 * @author Rhondu Smithwick
 */
public class ScriptTest {

    private static final String TELEPORT_PATH = "resources/providedScripts/Teleport.groovy";
    private final IEntitySystem universe = new EntitySystem();
    private String ID;

    @Before
    public void setUp() {
        final IEntity entity = new Entity();
        ID = entity.getID();
        universe.addEntity(entity);
    }

    @Test
    public void teleportTest() {
        IEntity entity = universe.getEntity(ID);
        entity.addComponent(new Position());
        Action action = new Action(TELEPORT_PATH);
        action.putParameter("entityID", ID);
        action.putParameter("dx", 50);
        action.putParameter("dy", 50);
        action.activate(universe);
        Position position = entity.getComponent(Position.class);
        assertEquals(position.getX(), 50, 0.001);
        assertEquals(position.getY(), 50, 0.001);
        System.out.println(action.getScript());
    }
}
