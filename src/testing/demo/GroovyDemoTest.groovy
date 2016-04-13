package testing.demo

import model.component.character.Health
import model.component.character.Score
import model.component.movement.Position
import model.component.movement.Velocity
import model.component.visual.ImagePath
import model.entity.Entity
import api.IEntity
import api.IEntitySystem
import api.ISystemManager

/**
 * Created by Tom on 4/13/2016.
 */
class GroovyDemoTest {
	String IMAGE_PATH = "resources/RhonduSmithwick.JPG"

	IEntity getCharacter0() {
		IEntity character = new Entity()
		character.addComponent(new Health((double) 100))
		character.addComponent(new Score((double) 100))
		Position pos = new Position(250.0, 250.0)
		character.addComponent(pos)
		character.addComponent(new ImagePath(IMAGE_PATH))
		character.addComponent(new Velocity(10.0, 10.0))
		return character
	}

	IEntity getCharacter1() {
		IEntity character = new Entity()
		character.addComponent(new Health((double) 100))
		character.addComponent(new Score((double) 100))
		Position pos = new Position(250.0, 250.0)
		character.addComponent(pos)
		character.addComponent(new ImagePath(IMAGE_PATH))
		character.addComponent(new Velocity(10.0, -10.0))
		return character
	}

	static void run(ISystemManager game) {
		IEntitySystem universe = game.getEntitySystem()
		GroovyDemoTest test = new GroovyDemoTest()
		universe.addEntities(test.getCharacter0(), test.getCharacter1())
	}

	static void saveSystem(IEntitySystem universe) {
		universe.serialize("test.sav")
	}

	static void loadSystem(ISystemManager game, String filename) {
		game.evaluate("test.sav")
	}
}
