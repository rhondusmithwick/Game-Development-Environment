package providedScripts

import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.movement.Velocity

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Movs a sprite using the Physics Engine.
 * @author Rhondu Smithwick
 */


@Field Double velocityX = containsVariable("velocityX") ? getDouble("velocityX") : 0.0;
@Field Double velocityY = containsVariable("velocityY") ? getDouble("velocityY") : 0.0;

def move = { entity ->
    if (entity.hasComponent(Velocity.class)) {
        Velocity velocity = entity.getComponent(Velocity.class);
        velocity.setVXY(velocityX, velocityY);
    }
}

workOnEntities(move);