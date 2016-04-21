package voogasalad_can18_cw223;

import static org.junit.Assert.assertEquals;

import api.ILevel;
import model.entity.Level;
import org.junit.Test;

import api.IEntity;
import api.ILevel;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.entity.Entity;

public class VoogaTesting {

	@Test
	public void testMovement() {
		Position pos = new Position(0.0, 0.0);
		Velocity v = new Velocity(1.0, 1.0, true);
		IEntity e = new Entity();
		e.forceAddComponent(pos, true);
		e.forceAddComponent(v, true);
		// IPhysicsEngine p = new PhysicsEngine(null);
		ILevel universe = new Level();
		universe.addEntity(e);
		// p.update(universe, 2);
		// System.out.println(pos.getX()+" "+pos.getY());
		assertEquals(Math.abs(pos.getX() - 2.0) < 0.0001, true);
		assertEquals(Math.abs(pos.getY() - 2.0) < 0.0001, true);
	}

	@Test
	public void testImpulse() {
		Position pos = new Position(0.0, 0.0);
		Velocity v = new Velocity(1.0, 1.0, true);
		IEntity e = new Entity();
		e.forceAddComponent(pos, true);
		e.forceAddComponent(v, true);
		// IPhysicsEngine p = new PhysicsEngine(null);
		ILevel universe = new Level();
		universe.addEntity(e);
		// p.applyImpulse(e, new Vector(10, 20));
		assertEquals(Math.abs(v.getVX() - 11.0) < 0.0001, true);
		assertEquals(Math.abs(v.getVY() - 21.0) < 0.0001, true);
	}

}
