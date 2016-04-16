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
		character.addComponent(new Velocity(10.0, 10.0, true))
		return character
	}

	IEntity getCharacter1() {
		IEntity character = new Entity()
		character.addComponent(new Health((double) 100))
		character.addComponent(new Score((double) 100))
		Position pos = new Position(250.0, 250.0)
		character.addComponent(pos)
		character.addComponent(new ImagePath(IMAGE_PATH))
		character.addComponent(new Velocity(10.0, -10.0, true))
		return character
	}

	static IEntity run(ISystemManager game) {
		IEntitySystem universe = game.getEntitySystem()
		GroovyDemoTest test = new GroovyDemoTest()
		IEntity s = test.getCharacter0()
		universe.addEntities(s)//, test.getCharacter1())
		return s
	}

	static void saveSystem(IEntitySystem universe) {
		universe.serialize("test.sav")
	}

	static void loadSystem(ISystemManager game, String filename) {
		game.evaluate("test.sav")
	}
}
