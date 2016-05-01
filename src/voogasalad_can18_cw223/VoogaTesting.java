package voogasalad_can18_cw223;

import api.IEntity;
import api.ILevel;
import api.IPhysicsEngine;
import javafx.geometry.Point2D;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.physics.Mass;
import model.entity.Entity;
import model.entity.Level;
import model.physics.PhysicsEngine;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VoogaTesting {

    @Test
    public void testMovement () {
        Position pos = new Position(0.0, 0.0);
        Velocity v = new Velocity(1.0, 1.0);
        IEntity e = new Entity();
        e.forceAddComponent(pos, true);
        e.forceAddComponent(v, true);
        IPhysicsEngine p = new PhysicsEngine();
        ILevel universe = new Level();
        universe.addEntity(e);
        p.update(universe, 2);
        System.out.println(pos.getX() + " " + pos.getY());
        assertEquals(Math.abs(pos.getX() - 2.0) < 0.0001, true);
        assertEquals(Math.abs(pos.getY() - 2.0) < 0.0001, true);
    }

<<<<<<< HEAD
	@Test
	public void testImpulse() {
		Position pos = new Position(0.0, 0.0);
		Velocity v = new Velocity(1.0, 1.0);
		IEntity e = new Entity();
		e.forceAddComponent(new Mass(1), true);
		e.forceAddComponent(pos, true);
		e.forceAddComponent(v, true);
		IPhysicsEngine p = new PhysicsEngine(new RealisticVelocityCalculator());
		ILevel universe = new Level();
		universe.addEntity(e);
		p.applyImpulse(e, new Point2D(10, 20));
		System.out.println(v.getVX() + " " + v.getVY());
		assertEquals(Math.abs(v.getVX() - 11.0) < 0.0001, true);
		assertEquals(Math.abs(v.getVY() - 21.0) < 0.0001, true);
	}
=======
    @Test
    public void testImpulse () {
        Position pos = new Position(0.0, 0.0);
        Velocity v = new Velocity(1.0, 1.0);
        IEntity e = new Entity();
        e.forceAddComponent(new Mass(1), true);
        e.forceAddComponent(pos, true);
        e.forceAddComponent(v, true);
        IPhysicsEngine p = new PhysicsEngine();
        ILevel universe = new Level();
        universe.addEntity(e);
        //p.applyImpulse(e, new Vector(10, 20));
        System.out.println(v.getVX() + " " + v.getVY());
        assertEquals(Math.abs(v.getVX() - 11.0) < 0.0001, true);
        assertEquals(Math.abs(v.getVY() - 21.0) < 0.0001, true);
    }
>>>>>>> de3d6a453d1ef89127c3c55d5c38b2326bb361f7

}
