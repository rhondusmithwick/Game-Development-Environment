package testing.demo;

import api.IEntity;
import api.ILevel;
import api.IPhysicsEngine;
import javafx.application.Application;
import javafx.stage.Stage;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.physics.Collision;
import model.component.physics.Mass;
import model.component.physics.RestitutionCoefficient;
import model.component.visual.ImagePath;
import model.entity.Entity;
import model.entity.Level;
import model.physics.PhysicsEngine;

public class PhysicsTest extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		testMomentum();
	}

	public void testMomentum() {
		IEntity e1 = new Entity("one");
		Position pos1 = new Position(0, 0);
		Velocity v1 = new Velocity(0, 1);
		Mass m1 = new Mass(2);
		ImagePath disp1 = new ImagePath();
		disp1.setImageWidth(4);
		disp1.setImageHeight(4);
		Collision col1 = new Collision("one");
		RestitutionCoefficient r1 = new RestitutionCoefficient(1.0);
		e1.addComponents(pos1, v1, m1, disp1, col1, r1);

		IEntity e2 = new Entity("two");
		Position pos2 = new Position(0, 20);
		Velocity v2 = new Velocity(0, -2);
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
		for (int i = 0; i < 20; i++) {
			p.update(universe, 1);
			System.out.println(pos1 + " -- "+pos2);
		}

		System.out.println(v1);
		System.out.println(v2);

		System.exit(0);
	}
}
