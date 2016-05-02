package providedScripts

import api.IEntity
import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.movement.Velocity
import model.component.movement.Position
import model.component.physics.Gravity
import model.component.visual.Sprite
import model.entity.Entity

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Spawns a fruit
 * @author Anirudh Jonnavithula
 */

// Parameter: velocityY: what to change y velocity to
@Field Double positionXField = containsVariable("positionX") ? getDouble("positionX") : 0.0;
@Field String fruitNameField = containsVariable("fruitName") ? getString("fruitName") : "";

IEntity entity = new Entity(fruitNameField);
entity.addComponent(new Position(positionXField, 0));
entity.addComponent(new Gravity(20));
entity.addComponent(new Velocity(0,0));
Sprite sprite = new Sprite("resources/images/blastoise.png");
sprite.setImageHeight(50);
sprite.setImageWidth(50);
entity.addComponent(sprite);
universe.getEntitySystem().addEntity(entity);