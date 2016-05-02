package providedScripts

import api.IEntity
import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.movement.Velocity
import model.component.movement.Position
import model.component.physics.Collision
import model.component.physics.Gravity
import model.component.visual.Sprite
import model.entity.Entity

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Spawns a sprite
 * @author Anirudh Jonnavithula
 */

// Parameter: velocityY: what to change y velocity to
@Field String entityNameField = containsVariable("entityName") ? getString("entityName") : "";
@Field String playerNameField = containsVariable("playerName") ? getString("playerName") : "";
@Field String directionField = containsVariable("direction") ? getString("direction") : "";
@Field String spritePathField = containsVariable("spritePath") ? getString("spritePath") : "";

IEntity entity = new Entity(entityNameField);
Position playerPosition = universe.getEntitiesWithName(playerNameField).get(0).getComponent(Position.class);
entity.addComponent(new Position(playerPosition.getX(), playerPosition.getY()));
if(directionField.equals("east")) {
    entity.addComponent(new Velocity(300,0));
}
else {
    entity.addComponent(new Velocity(-300,0));
}
Sprite sprite = new Sprite(spritePathField);
sprite.setImageHeight(20);
sprite.setImageWidth(20);
entity.addComponent(sprite);
entity.addComponent(new Collision(playerNameField));
universe.getEntitySystem().addEntity(entity);