package testing.demo

import api.ILevel
import javafx.scene.image.ImageView
import model.component.character.Health
import model.component.character.Score
import model.component.movement.Position
import model.component.movement.Velocity
import model.component.physics.Collision
import model.component.physics.Gravity
import model.component.physics.Mass
import model.component.physics.RestitutionCoefficient
import model.component.visual.ImagePath
import model.entity.Entity
import api.IEntity
import api.ILevel
import api.ISystemManager

/**
 * Created by Tom on 4/13/2016.
 */
class GroovyDemoTest {
	IEntity getRhondu() {
		IEntity character = new Entity("Rhondu")
		character.addComponent(new Health((double) 100))
		character.addComponent(new Score((double) 100))
		Position pos = new Position(50.0, 0.0)
		character.addComponent(pos)
		ImagePath path = new ImagePath();
		ImageView img = path.getImageView();
		img.setScaleX(0.10)
		img.setScaleY(0.10)
		character.addComponents(path, new Velocity(20.0, 0.0), new Gravity(200),
				new Collision("rhondu"), new RestitutionCoefficient(1.0), new Mass(5))
		return character
	}

	IEntity getPlatform() {
		IEntity platform = new Entity("Platform");
		ImagePath path = new ImagePath();
		ImageView img = path.getImageView();
		//				img.setScaleX(0.10)
		//				img.setScaleY(0.10)
		platform.addComponents(path, new Position(100, 300), new Gravity(0),
				new Collision("platform"), new RestitutionCoefficient(1.0), new Mass(100));
		return platform;
	}

	void run(ISystemManager game) {
		ILevel universe = game.getEntitySystem()
		universe.addEntities(this.getRhondu(), this.getPlatform());
	}
}
