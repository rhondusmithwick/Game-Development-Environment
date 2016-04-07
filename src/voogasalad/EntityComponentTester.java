package voogasalad;

import static org.junit.Assert.*;

import org.junit.Test;

import model.component.character.Health;
import model.entity.Entity;

public class EntityComponentTester {

	@Test
	public void getEntityID() {
		Entity entity = new Entity(0);
		assertEquals(0, entity.getID());
	}
	
	@Test
	public void addHealthComponent() {
		Entity entity = new Entity(0);
		entity.addComponent(new Health(50.0));
		assertEquals(true, entity.hasComponent(Health.class));
		
	}
}
