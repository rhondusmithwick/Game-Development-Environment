package testing.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import api.IEntity;
import api.ILevel;
import api.IPhysicsEngine;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.physics.Collision;
import model.component.physics.Mass;
import model.component.physics.RestitutionCoefficient;
import model.component.visual.ImagePath;
import model.entity.Entity;
import model.entity.Level;
import model.physics.PhysicsEngine;

public class PhysicsTest {

	@Test
	public void momentum0() {
		IEntity e1 = new Entity("one");
		Position pos1 = new Position(0, 0);
		Velocity v1 = new Velocity(1, 0);
		Mass m1 = new Mass(2);
		ImagePath disp1 = new ImagePath("resources/testing/RhonduSmithwick.JPG");
		disp1.setImageWidth(4);
		disp1.setImageHeight(4);
		Collision col1 = new Collision("one");
		RestitutionCoefficient r1 = new RestitutionCoefficient(1.0);
		e1.addComponents(pos1, v1, m1, disp1, col1, r1);

		IEntity e2 = new Entity("two");
		Position pos2 = new Position(0, 20);
		Velocity v2 = new Velocity(-2, 0);
		Mass m2 = new Mass(3);
		ImagePath disp2 = new ImagePath();
		disp2.setImageWidth(4);
		disp2.setImageHeight(4);
		Collision col2 = new Collision("two");
		RestitutionCoefficient r2 = new RestitutionCoefficient(1.0);
		e2.addComponents(pos2, v2, m2, disp2, col2, r2);

		ILevel universe = new Level();
		universe.addEntities(e1, e2);
		IPhysicsEngine p = new PhysicsEngine();
		for (int i = 0; i < 10; i++) {
			p.update(universe, 1);
		}

		double v1x = v1.getVX();
		double v1y = v2.getVY();
		assertEquals(Math.abs(v1x + 2.6) < 0.001, true);
		assertEquals(Math.abs(v1y) < 0.001, true);

		double v2x = v1.getVX();
		double v2y = v2.getVY();
		assertEquals(Math.abs(v2x + 0.4) < 0.001, true);
		assertEquals(Math.abs(v2y) < 0.001, true);
	}

}
