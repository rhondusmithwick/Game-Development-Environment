package testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.entity.Entity;
import model.entity.EntitySystem;
import view.EditorEnvironment;


public class BrunaTesting {
	
	 private EditorEnvironment myEditorEnvironment;

	@Before
	    public void setUp () {
	        myEditorEnvironment = new EditorEnvironment(null);
	    }

	@Test
	public void testAcceptsEntitySystem() {
		EntitySystem entitySystem = new EntitySystem();
		myEditorEnvironment.addEntitySystem(entitySystem);
		assertNotNull(myEditorEnvironment.getEntitySystem());
	}
	
	@Test 
	public void testContainsEntities() {
		EntitySystem entitySystem = new EntitySystem();
		entitySystem.addEntities(new Entity(0));
		myEditorEnvironment.addEntitySystem(entitySystem);
		assertNotNull(myEditorEnvironment.getEntity(0));
	}

}
