package voogasalad;

import model.component.character.Health;
import model.entity.Entity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EntityComponentTester {

    @Test
    public void getEntityID () {
        Entity entity = new Entity();
        assertEquals(0, entity.getID());
    }

    @Test
    public void addHealthComponent () {
        Entity entity = new Entity();
        entity.addComponent(new Health(50.0));
        assertEquals(true, entity.hasComponent(Health.class));

    }
}
