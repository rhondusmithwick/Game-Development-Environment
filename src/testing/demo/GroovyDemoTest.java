package testing.demo;

import javafx.scene.image.ImageView;
import model.component.character.Health;
import model.component.character.Score;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.physics.Collision;
import model.component.physics.Gravity;
import model.component.physics.Mass;
import model.component.physics.RestitutionCoefficient;
import model.component.visual.ImagePath;
import model.entity.Entity;

import java.util.Arrays;

import api.IEntity;
import api.IEntitySystem;
import api.ISystemManager;

/**
 * Created by Tom on 4/13/2016.
 */
class GroovyDemoTest {
	IEntity getRhondu() {
		IEntity character = new Entity();
		character.addComponent(new Health((double) 100));
		character.addComponent(new Score((double) 100));
		Position pos = new Position(200.0, 0.0);
		character.addComponent(pos);
		ImagePath path = new ImagePath();
		ImageView img = path.getImageView();
		img.setScaleX(0.10);
		img.setScaleY(0.10);
		character.addComponents(path, new Velocity(20.0, -20.0), new Gravity(400),
				new Collision(Arrays.asList("rhondu")), new RestitutionCoefficient(0.2), new Mass(5));
		return character;
	}

	IEntity getPlatform() {
		IEntity platform = new Entity();
		ImagePath path = new ImagePath();
		ImageView img = path.getImageView();
		//		img.setScaleX(0.10)
		//		img.setScaleY(0.10)
		platform.addComponents(path, new Position(100, 300), new Collision(Arrays.asList("platform")), new RestitutionCoefficient(0.2), new Mass(100));
		return platform;
	}

	IEntity run(ISystemManager game) {
		IEntitySystem universe = game.getEntitySystem();
		IEntity r = this.getRhondu();
		universe.addEntities(r, this.getPlatform());
		return r;
	}
}
