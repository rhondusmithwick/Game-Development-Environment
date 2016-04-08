package testing

import api.IEntity
import model.component.character.Health
import model.component.character.Score
import model.component.movement.Position
import model.component.movement.Velocity
import model.component.visual.ImagePath
import model.entity.Entity

/**
 * Created by Tom on 4/7/2016.
 */
class BenTestCharacter {
    IEntity run(String IMAGE_PATH) {
        IEntity character = new Entity(0);
        character.forceAddComponent(new Health((double) 100), true);
        character.forceAddComponent(new Score((double) 100), true);
        Position pos = new Position(250.0, 250.0);
        character.forceAddComponent(pos, true);
        character.forceAddComponent(new ImagePath(IMAGE_PATH), true);
        character.forceAddComponent(new Velocity(10.0, 10.0), true);
        return character;
    }
}
