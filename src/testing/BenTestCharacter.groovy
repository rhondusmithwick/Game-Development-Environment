package testing

import model.component.character.Health
import model.component.character.Score
import model.component.movement.Position
import model.component.movement.Velocity
import model.component.physics.Collision
import model.component.physics.Gravity
import model.component.physics.Mass
import model.component.visual.ImagePath
import model.entity.Entity
import api.IEntity

/**
 * Created by Tom on 4/7/2016.
 */
class BenTestCharacter {
	static IEntity run(String IMAGE_PATH) {
		IEntity character = new Entity()
		character.addComponent(new Health((double) 100))
		character.addComponent(new Score((double) 100))
		Position pos = new Position(100.0, 250.0)
		character.addComponent(pos)
		character.addComponent(new ImagePath(IMAGE_PATH))
		character.addComponent(new Velocity(50.0, 0.0))
		character.addComponent(new Mass(30.0))
		character.addComponent(new Gravity())
		character.addComponent(new Collision(null))
		return character
	}
}
